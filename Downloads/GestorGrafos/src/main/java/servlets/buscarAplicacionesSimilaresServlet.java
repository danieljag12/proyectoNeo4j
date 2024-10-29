package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;

/**
 *
 * @author danie
 */
@WebServlet(name = "buscarAplicacionesSimilaresServlet", urlPatterns = {"/buscarAplicacionesSimilaresServlet"})
public class buscarAplicacionesSimilaresServlet extends HttpServlet {

    private final String NEO4J_URI = "bolt://localhost:7687"; // Cambia esto según tu configuración
    private final String NEO4J_USER = "neo4j"; // Cambia esto según tu configuración
    private final String NEO4J_PASSWORD = "12345678"; // Cambia esto según tu configuración

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String applicationName = request.getParameter("applicationName");
        List<Map<String, Object>> aplicacionesSimilares = new ArrayList<>();

        // Conectar a Neo4j
        try (Session session = GraphDatabase.driver(NEO4J_URI, AuthTokens.basic(NEO4J_USER, NEO4J_PASSWORD)).session()) {
            // Consulta para buscar aplicaciones similares
            String query = "MATCH (a1:Aplicación {titulo: $titulo})-[:USA]->(t:Tecnología)<-[:USA]-(a2:Aplicación) " +
                           "WHERE a1 <> a2 " +
                           "RETURN a2.titulo AS aplicaciones_similares, count(t) AS tecnologias_compartidas " +
                           "ORDER BY tecnologias_compartidas DESC";

            // Ejecutar la consulta
            Result result = session.run(query, org.neo4j.driver.Values.parameters("titulo", applicationName));

            // Procesar los resultados
            while (result.hasNext()) {
                var record = result.next();
                Map<String, Object> aplicacion = new HashMap<>();
                aplicacion.put("aplicaciones_similares", record.get("aplicaciones_similares").asString());
                aplicacion.put("tecnologias_compartidas", record.get("tecnologias_compartidas").asInt());
                aplicacionesSimilares.add(aplicacion);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al conectar a la base de datos.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        // Almacenar los resultados en el request y redirigir al JSP
        request.setAttribute("aplicacionesSimilares", aplicacionesSimilares);
        request.setAttribute("applicationName", applicationName);
        request.getRequestDispatcher("buscarAplicacionesSimilares.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}