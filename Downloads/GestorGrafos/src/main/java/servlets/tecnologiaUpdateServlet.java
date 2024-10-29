package servlets;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;
import org.neo4j.driver.Result;
import org.neo4j.driver.Record;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

@WebServlet(name = "tecnologiaUpdateServlet", urlPatterns = {"/tecnologiaUpdateServlet"})
public class tecnologiaUpdateServlet extends HttpServlet {

    private static final String NEO4J_URI = "bolt://localhost:7687";
    private static final String NEO4J_USERNAME = "neo4j";
    private static final String NEO4J_PASSWORD = "12345678";
    private Driver driver;

    @Override
    public void init() throws ServletException {
        driver = GraphDatabase.driver(NEO4J_URI, AuthTokens.basic(NEO4J_USERNAME, NEO4J_PASSWORD));
    }

    @Override
    public void destroy() {
        if (driver != null) {
            driver.close();
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String nombre = request.getParameter("nombre");

        if (nombre == null || nombre.isEmpty()) {
            response.getWriter().println("<script>alert('Nombre no puede estar vacío.'); window.history.back();</script>");
            return;
        }

        try (Session session = driver.session()) {
            if ("load".equals(action)) {
                // Cargar información de la tecnología
                String query = "MATCH (t:Tecnología {nombre: $nombre}) RETURN t.nombre AS nombre";
                Result result = session.run(query, Map.of("nombre", nombre));

                if (result.hasNext()) {
                    Record record = result.next();
                    request.setAttribute("nombre", nombre);
                    request.setAttribute("nuevoNombre", record.get("nombre").asString());

                    request.getRequestDispatcher("tecnologiaUpdate.jsp").forward(request, response);
                } else {
                    response.getWriter().println("<script>alert('Tecnología no encontrada.'); window.history.back();</script>");
                }
            } else {
                // Actualizar información de la tecnología
                String nuevoNombre = request.getParameter("nuevoNombre");

                if (nuevoNombre == null || nuevoNombre.isEmpty()) {
                    response.getWriter().println("<script>alert('El nuevo nombre no puede estar vacío.'); window.history.back();</script>");
                    return;
                }

                String updateQuery = "MATCH (t:Tecnología {nombre: $nombre}) SET t.nombre = $nuevoNombre RETURN t";
                Result result = session.run(updateQuery, Map.of("nombre", nombre, "nuevoNombre", nuevoNombre));

                if (result.hasNext()) {
                    response.getWriter().println("<script>alert('Tecnologia actualizada con exito.'); window.location.href = 'CRUDTecnologia.jsp';</script>");
                } else {
                    response.getWriter().println("<script>alert('No se encontró la tecnología para actualizar.'); window.history.back();</script>");
                }
            }
        } catch (Exception e) {
            response.getWriter().println("<script>alert('Error: " + e.getMessage() + "'); window.history.back();</script>");
        }
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
        return "Servlet para cargar y actualizar datos de una tecnología en la base de datos Neo4j";
    }
}