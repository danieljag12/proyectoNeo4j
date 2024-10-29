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

@WebServlet(name = "ubicacionUpdateServlet", urlPatterns = {"/ubicacionUpdateServlet"})
public class ubicacionUpdateServlet extends HttpServlet {

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
        String pais = request.getParameter("pais");

        if (pais == null || pais.isEmpty()) {
            response.getWriter().println("<script>alert('Nombre del país no puede estar vacío.'); window.history.back();</script>");
            return;
        }

        try (Session session = driver.session()) {
            if ("load".equals(action)) {
                // Cargar información de la ubicación
                String query = "MATCH (u:Ubicación {pais: $pais}) RETURN u.pais AS pais";
                Result result = session.run(query, Map.of("pais", pais));

                if (result.hasNext()) {
                    Record record = result.next();
                    request.setAttribute("pais", pais);
                    request.setAttribute("nuevoPais", record.get("pais").asString());

                    request.getRequestDispatcher("ubicacionUpdate.jsp").forward(request, response);
                } else {
                    response.getWriter().println("<script>alert('Ubicación no encontrada.'); window.history.back();</script>");
                }
            } else {
                // Actualizar información de la ubicación
                String nuevoPais = request.getParameter("nuevoPais");

                if (nuevoPais == null || nuevoPais.isEmpty()) {
                    response.getWriter().println("<script>alert('El nuevo nombre del país no puede estar vacío.'); window.history.back();</script>");
                    return;
                }

                String updateQuery = "MATCH (u:Ubicación {pais: $pais}) SET u.pais = $nuevoPais RETURN u";
                Result result = session.run(updateQuery, Map.of("pais", pais, "nuevoPais", nuevoPais));

                if (result.hasNext()) {
                    response.getWriter().println("<script>alert('Ubicación actualizada con éxito.'); window.location.href = 'CRUDUbicacion.jsp';</script>");
                } else {
                    response.getWriter().println("<script>alert('No se encontró la ubicación para actualizar.'); window.history.back();</script>");
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
        return "Servlet para cargar y actualizar datos de una ubicación en la base de datos Neo4j";
    }
}
