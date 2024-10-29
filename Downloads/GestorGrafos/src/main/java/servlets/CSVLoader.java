package servlets;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CSVLoader {
    private static final String NEO4J_URI = "bolt://localhost:7687";
    private static final String NEO4J_USERNAME = "neo4j";
    private static final String NEO4J_PASSWORD = "12345678";
    private static final String IMPORT_FOLDER = "C:\\Users\\danie\\.Neo4jDesktop\\relate-data\\dbmss\\dbms-c1bafd0a-582e-4b49-9397-1dc3f63c04cf\\import\\";

    public static void loadCsv(InputStream csvInputStream, String fileName) throws IOException {
        String destinationPath = IMPORT_FOLDER + fileName;

        // Guardar el archivo CSV en la carpeta de importación
        try (FileOutputStream outStream = new FileOutputStream(destinationPath)) {
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = csvInputStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
        }

        // Formatear el nombre del archivo para la URL de Neo4j
        String formattedFileName = fileName.replace(" ", "%20");
        
        // Cargar el archivo CSV en Neo4j
        loadCsvToNeo4j(formattedFileName);
    }

    private static void loadCsvToNeo4j(String fileName) {
        Driver driver = GraphDatabase.driver(NEO4J_URI, AuthTokens.basic(NEO4J_USERNAME, NEO4J_PASSWORD));

        String loadCsvQuery = "LOAD CSV WITH HEADERS FROM 'file:///" + fileName + "' AS row " +
                "WITH row, trim(toLower(row.`Built With`)) AS tecnologias, trim(toLower(row.By)) AS desarrolladores, trim(toLower(row.Location)) AS ubicacion " +
                "WHERE row.Title IS NOT NULL AND tecnologias IS NOT NULL AND desarrolladores IS NOT NULL AND ubicacion IS NOT NULL " +
                "MERGE (app:Aplicación {titulo: row.Title, subtitulo: coalesce(row.`Sub-Title`, ''), enlaceYouTube: row.`YouTube Link`, funcionalidad: row.`What it Does`, enlace: row.`Project Link`}) " +
                "WITH app, split(tecnologias, ',') AS techs, split(desarrolladores, ',') AS devs, ubicacion " +
                "UNWIND techs AS tech " +
                "MERGE (t:Tecnología {nombre: trim(tech)}) " +
                "MERGE (app)-[:USA]->(t) " +
                "WITH app, devs, ubicacion " +
                "UNWIND devs AS dev " +
                "MERGE (d:Desarrollador {nombre: trim(dev)}) " +
                "MERGE (app)-[:CREADA_POR]->(d) " +
                "WITH d, ubicacion " +
                "MERGE (u:Ubicación {pais: ubicacion}) " +
                "MERGE (d)-[:UBICADO_EN]->(u)";

        try (Session session = driver.session()) {
            session.writeTransaction(tx -> {
                tx.run(loadCsvQuery);
                System.out.println("Archivo CSV cargado en Neo4j exitosamente.");
                return null;
            });
        } catch (Exception e) {
            System.err.println("Error al ejecutar el comando LOAD CSV en Neo4j: " + e.getMessage());
        } finally {
            driver.close();
        }
    }
}
