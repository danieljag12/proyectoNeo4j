package servlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;

@WebServlet(name = "desarrolladorUnicoReadServlet", urlPatterns = {"/desarrolladorUnicoReadServlet"})
public class desarrolladorUnicoReadServlet extends HttpServlet {

    private final String NEO4J_URI = "bolt://localhost:7687"; // Ajusta según tu configuración
    private final String NEO4J_USER = "neo4j"; // Ajusta según tu configuración
    private final String NEO4J_PASSWORD = "12345678"; // Ajusta según tu configuración

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nombreDesarrollador = request.getParameter("nombreDesarrollador");
        String nombre = null;

        try (Session session = GraphDatabase.driver(NEO4J_URI, AuthTokens.basic(NEO4J_USER, NEO4J_PASSWORD)).session()) {
            String query = "MATCH (d:Desarrollador {nombre: $nombre}) RETURN d.nombre AS nombre";
            Result result = session.run(query, org.neo4j.driver.Values.parameters("nombre", nombreDesarrollador));

            if (result.hasNext()) {
                var record = result.next();
                nombre = record.get("nombre").asString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al conectar a la base de datos.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        request.setAttribute("nombre", nombre);
        request.setAttribute("nombreDesarrollador", nombreDesarrollador);
        request.getRequestDispatcher("desarrolladorUnicoRead.jsp").forward(request, response);
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
        return "Servlet para leer un desarrollador único";
    }
}