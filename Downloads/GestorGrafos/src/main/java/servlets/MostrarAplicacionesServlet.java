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
import org.neo4j.driver.Record;

@WebServlet(name = "MostrarAplicacionesServlet", urlPatterns = {"/MostrarAplicacionesServlet"})
public class MostrarAplicacionesServlet extends HttpServlet {

    private final String NEO4J_URI = "bolt://localhost:7687"; // Cambia esto según tu configuración
    private final String NEO4J_USER = "neo4j"; // Cambia esto según tu configuración
    private final String NEO4J_PASSWORD = "12345678"; // Cambia esto según tu configuración

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String regionNombre = request.getParameter("regionNombre");
        Map<String, Object> resultado = new HashMap<>();
        List<Map<String, String>> aplicaciones = new ArrayList<>();

        // Conectar a Neo4j
        try (Session session = GraphDatabase.driver(NEO4J_URI, AuthTokens.basic(NEO4J_USER, NEO4J_PASSWORD)).session()) {
            String query = "MATCH (u:Ubicación {pais: $nombreDeRegion})<-[:UBICADO_EN]-(d:Desarrollador) " +
                           "MATCH (a:Aplicación)-[:CREADA_POR]->(d) " +
                           "RETURN a.titulo AS Aplicación, d.nombre AS Desarrollador " +
                           "ORDER BY a.titulo LIMIT 25";

            // Ejecutar la consulta
            Result result = session.run(query, org.neo4j.driver.Values.parameters("nombreDeRegion", regionNombre));

            // Procesar el resultado
            while (result.hasNext()) {
                Record record = result.next();
                Map<String, String> appData = new HashMap<>();
                appData.put("Aplicación", record.get("Aplicación").asString());
                appData.put("Desarrollador", record.get("Desarrollador").asString());
                aplicaciones.add(appData);
            }
            resultado.put("aplicaciones", aplicaciones);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al conectar a la base de datos.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        // Almacenar el resultado en el request y redirigir al JSP
        request.setAttribute("resultado", resultado);
        request.setAttribute("regionNombre", regionNombre);
        request.getRequestDispatcher("mostrarAplicaciones.jsp").forward(request, response);
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
        return "Servlet para mostrar aplicaciones por región";
    }
}
