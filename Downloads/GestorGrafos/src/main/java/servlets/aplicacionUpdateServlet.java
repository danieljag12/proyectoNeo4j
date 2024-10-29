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

@WebServlet(name = "aplicacionUpdateServlet", urlPatterns = {"/aplicacionUpdateServlet"})
public class aplicacionUpdateServlet extends HttpServlet {

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
        String titulo = request.getParameter("titulo");

        if (titulo == null || titulo.isEmpty()) {
            response.getWriter().println("<script>alert('Título no puede estar vacío.'); window.history.back();</script>");
            return;
        }

        try (Session session = driver.session()) {
            if ("load".equals(action)) {
                // Cargar información de la aplicación
                String query = "MATCH (a:Aplicación {titulo: $titulo}) RETURN a.subtitulo AS subtitulo, " +
                               "a.funcionalidad AS funcionalidad, a.enlaceYouTube AS enlaceYouTube, " +
                               "a.enlace AS enlace";
                Result result = session.run(query, Map.of("titulo", titulo));

                if (result.hasNext()) {
                    Record record = result.next();
                    request.setAttribute("titulo", titulo);
                    request.setAttribute("subtitulo", record.get("subtitulo").asString());
                    request.setAttribute("funcionalidad", record.get("funcionalidad").asString());
                    request.setAttribute("enlaceYouTube", record.get("enlaceYouTube").asString());
                    request.setAttribute("enlace", record.get("enlace").asString());

                    request.getRequestDispatcher("aplicacionUpdate.jsp").forward(request, response);
                } else {
                    response.getWriter().println("<script>alert('Aplicación no encontrada.'); window.history.back();</script>");
                }
            } else {
                // Actualizar información de la aplicación
                String subtitulo = request.getParameter("subtitulo");
                String funcionalidad = request.getParameter("funcionalidad");
                String enlaceYouTube = request.getParameter("enlaceYouTube");
                String enlace = request.getParameter("enlace");

                if (subtitulo == null || funcionalidad == null || enlaceYouTube == null || enlace == null) {
                    response.getWriter().println("<script>alert('Los campos no pueden estar vacíos.'); window.history.back();</script>");
                    return;
                }

                String updateQuery = "MATCH (a:Aplicación {titulo: $titulo}) " +
                                     "SET a.subtitulo = $subtitulo, a.funcionalidad = $funcionalidad, " +
                                     "a.enlaceYouTube = $enlaceYouTube, a.enlace = $enlace " +
                                     "RETURN a";
                Result result = session.run(updateQuery, Map.of("titulo", titulo, "subtitulo", subtitulo,
                                                                "funcionalidad", funcionalidad, "enlaceYouTube", enlaceYouTube, "enlace", enlace));

                if (result.hasNext()) {
                    response.getWriter().println("<script>alert('Aplicacion actualizada con exito.'); window.location.href = 'CRUDAplicacion.jsp';</script>");
                } else {
                    response.getWriter().println("<script>alert('No se encontró la aplicación para actualizar.'); window.history.back();</script>");
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
        return "Servlet para cargar y actualizar datos de una aplicación en la base de datos Neo4j";
    }
}