package servlets;

import conexion.Neo4jConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.neo4j.driver.Session;
import org.neo4j.driver.Result;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(name = "ubicacionDeleteServlet", urlPatterns = {"/ubicacionDeleteServlet"})
public class ubicacionDeleteServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String pais = request.getParameter("pais");

        try (PrintWriter out = response.getWriter()) {
            // Conectar a Neo4j
            Neo4jConnection neo4j = new Neo4jConnection("bolt://localhost:7687", "neo4j", "12345678");
            try (Session session = neo4j.getSession()) {
                // Eliminar el nodo "Ubicación" de la base de datos
                String eliminarUbicacion = "MATCH (u:Ubicación {pais: $pais}) DETACH DELETE u RETURN COUNT(u) > 0 AS deleted";
                Result result = session.run(eliminarUbicacion, Map.of("pais", pais));

                // Confirmar la eliminación de la ubicación
                if (result.hasNext() && result.single().get("deleted").asBoolean()) {
                    out.println("<script>alert('Ubicación eliminada exitosamente.'); window.location='CRUDUbicacion.jsp';</script>");
                } else {
                    out.println("<script>alert('Error: No se encontró una ubicación con ese país.'); window.history.back();</script>");
                }
            } catch (Exception e) {
                out.println("<script>alert('Error al eliminar la ubicación: " + e.getMessage() + "'); window.history.back();</script>");
            } finally {
                // Cerrar la conexión
                neo4j.close();
            }
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
        return "Servlet para eliminar ubicaciones en Neo4j";
    }
}