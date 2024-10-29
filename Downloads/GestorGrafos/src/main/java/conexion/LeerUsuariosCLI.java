package conexion;


import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Record;

public class LeerUsuariosCLI {
    private static final String URI = "bolt://localhost:7687";
    private static final String USERNAME = "neo4j";
    private static final String PASSWORD = "12345678";

    public static void main(String[] args) {
        try (Driver driver = GraphDatabase.driver(URI, AuthTokens.basic(USERNAME, PASSWORD))) {
            listarUsuarios(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void listarUsuarios(Driver driver) {
        try (Session session = driver.session()) {
            String query = "MATCH (t:Tecnología)<-[:USA]-(a:Aplicación)\n" +
                            "WITH t, count(a) AS num_aplicaciones\n" +
                            "WHERE num_aplicaciones > 10\n" +
                            "RETURN t.nombre AS Tecnologia, num_aplicaciones AS TotalAplicaciones\n" +
                            "ORDER BY num_aplicaciones DESC\n" +
                            "LIMIT 5";
            Result result = session.run(query);

            System.out.println("Tecnologías con más de 10 aplicaciones:");
            while (result.hasNext()) {
                Record record = result.next();
                System.out.println("Tecnología: " + record.get("Tecnologia").asString() + 
                                   ", TotalAplicaciones: " + record.get("TotalAplicaciones").asInt());
            }
        }
    }
}