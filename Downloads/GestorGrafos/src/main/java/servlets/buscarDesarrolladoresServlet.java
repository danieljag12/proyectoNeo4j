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

@WebServlet(name = "buscarDesarrolladoresServlet", urlPatterns = {"/buscarDesarrolladoresServlet"})
public class buscarDesarrolladoresServlet extends HttpServlet {

    private final String NEO4J_URI = "bolt://localhost:7687"; // Cambia esto según tu configuración
    private final String NEO4J_USER = "neo4j"; // Cambia esto según tu configuración
    private final String NEO4J_PASSWORD = "12345678"; // Cambia esto según tu configuración

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String tecnologia1 = request.getParameter("tecnologia1");
        String tecnologia2 = request.getParameter("tecnologia2");
        Map<String, Object> resultado = new HashMap<>();

        // Conectar a Neo4j
        try (Session session = GraphDatabase.driver(NEO4J_URI, AuthTokens.basic(NEO4J_USER, NEO4J_PASSWORD)).session()) {
            // Consulta para obtener desarrolladores
            String query = "WITH [$tecnologia1, $tecnologia2] AS tecnologias_seleccionadas " +
                           "MATCH (t:Tecnología) " +
                           "WHERE t.nombre IN tecnologias_seleccionadas " +
                           "WITH tecnologias_seleccionadas, collect(t) AS tecnologias_necesarias " +
                           "MATCH (d:Desarrollador)-[:CREADA_POR]-(a:Aplicación)-[:USA]->(t:Tecnología) " +
                           "WHERE t IN tecnologias_necesarias " +
                           "WITH d, tecnologias_seleccionadas, collect(DISTINCT t.nombre) AS tecnologias_desarrolladas, tecnologias_necesarias " +
                           "WHERE size(tecnologias_desarrolladas) >= size(tecnologias_necesarias) " +
                           "WITH collect(d) AS desarrolladores, tecnologias_seleccionadas " +
                           "UNWIND desarrolladores AS d1 " +
                           "UNWIND desarrolladores AS d2 " +
                           "WITH d1, d2, tecnologias_seleccionadas " +
                           "WHERE id(d1) < id(d2) " +
                             "AND NOT (d1)-[:CREADA_POR]->(:Aplicación)<-[:CREADA_POR]-(d2) " +
                           "RETURN d1.nombre AS Desarrollador1, d2.nombre AS Desarrollador2";

            // Ejecutar la consulta
            Result result = session.run(query, org.neo4j.driver.Values.parameters("tecnologia1", tecnologia1, "tecnologia2", tecnologia2));

            List<Map<String, String>> desarrolladores = new ArrayList<>();
            while (result.hasNext()) {
                var record = result.next();
                Map<String, String> pair = new HashMap<>();
                pair.put("Desarrollador1", record.get("Desarrollador1").asString());
                pair.put("Desarrollador2", record.get("Desarrollador2").asString());
                desarrolladores.add(pair);
            }

            resultado.put("desarrolladores", desarrolladores);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al conectar a la base de datos.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        // Almacenar el resultado en el request y redirigir al JSP
        request.setAttribute("resultado", resultado);
        request.setAttribute("tecnologia1", tecnologia1);
        request.setAttribute("tecnologia2", tecnologia2);
        request.getRequestDispatcher("contarDesarrolladores.jsp").forward(request, response);
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
        return "Servlet para buscar desarrolladores por tecnologías";
    }
}
