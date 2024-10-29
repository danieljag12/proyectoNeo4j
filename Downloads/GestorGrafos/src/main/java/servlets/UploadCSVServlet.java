package servlets;

import servlets.CSVLoader;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "UploadCSVServlet", urlPatterns = {"/UploadCSVServlet"})
@MultipartConfig // Habilita el soporte multipart
public class UploadCSVServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Part filePart = request.getPart("csvFile");

        if (filePart != null) {
            String fileName = filePart.getSubmittedFileName();

            try (InputStream csvInputStream = filePart.getInputStream()) {
                // Cargar el archivo CSV en Neo4j
                CSVLoader.loadCsv(csvInputStream, fileName);

                // Obtener los datos de Neo4j
                List<String[]> applicationsData = getApplicationsData();

                // Establecer los datos en el request
                request.setAttribute("csvData", applicationsData);
                request.setAttribute("message", "Archivo CSV cargado exitosamente."); // Mensaje de éxito

                // Redirigir al JSP para mostrar los datos
                request.getRequestDispatcher("displayCSV.jsp").forward(request, response);
            } catch (IOException e) {
                // Manejo de error al cargar el archivo
                request.setAttribute("error", "Error al cargar el archivo CSV: " + e.getMessage());
                request.getRequestDispatcher("displayCSV.jsp").forward(request, response);
            } catch (Exception e) {
                // Manejo de error al consultar Neo4j
                request.setAttribute("error", "Error al consultar los datos: " + e.getMessage());
                request.getRequestDispatcher("displayCSV.jsp").forward(request, response);
            }
        } else {
            // Manejo de error: el archivo no fue encontrado
            request.setAttribute("error", "No se encontró el archivo CSV.");
            request.getRequestDispatcher("displayCSV.jsp").forward(request, response);
        }
    }

    private List<String[]> getApplicationsData() {
        List<String[]> applicationsData = new ArrayList<>();

        // Conectar a Neo4j y ejecutar la consulta
        try (var driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "12345678"))) {
            try (Session session = driver.session()) {
                Result result = session.run("MATCH (a:Aplicación) RETURN a.titulo, a.subtitulo, a.enlaceYouTube, a.funcionalidad, a.enlace");
                while (result.hasNext()) {
                    var record = result.next();
                    String[] row = new String[5];
                    row[0] = record.get("a.titulo").asString();
                    row[1] = record.get("a.subtitulo").asString();
                    row[2] = record.get("a.enlaceYouTube").asString();
                    row[3] = record.get("a.funcionalidad").asString();
                    row[4] = record.get("a.enlace").asString();
                    applicationsData.add(row);
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Manejo de excepciones
        }

        return applicationsData;
    }
}
