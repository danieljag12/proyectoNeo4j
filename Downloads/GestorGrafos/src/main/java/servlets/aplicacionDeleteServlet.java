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

@WebServlet(name = "aplicacionDeleteServlet", urlPatterns = {"/aplicacionDeleteServlet"})
public class aplicacionDeleteServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String titulo = request.getParameter("titulo");

        try (PrintWriter out = response.getWriter()) {
            // Conectar a Neo4j
            Neo4jConnection neo4j = new Neo4jConnection("bolt://localhost:7687", "neo4j", "12345678");
            try (Session session = neo4j.getSession()) {
                // Eliminar el nodo "Aplicación" de la base de datos
                String eliminarAplicacion = "MATCH (a:Aplicación {titulo: $titulo}) DETACH DELETE a RETURN COUNT(a) > 0 AS deleted";
                Result result = session.run(eliminarAplicacion, Map.of("titulo", titulo));

                // Confirmar la eliminación de la aplicación
                if (result.hasNext() && result.single().get("deleted").asBoolean()) {
                    out.println("<script>alert('Aplicación eliminada exitosamente.'); window.location='CRUDAplicacion.jsp';</script>");
                } else {
                    out.println("<script>alert('Error: No se encontró una aplicación con ese título.'); window.history.back();</script>");
                }
            } catch (Exception e) {
                out.println("<script>alert('Error al eliminar la aplicación: " + e.getMessage() + "'); window.history.back();</script>");
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
        return "Servlet para eliminar aplicaciones en Neo4j";
    }
}