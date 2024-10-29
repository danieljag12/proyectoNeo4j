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

@WebServlet(name = "desarrolladorDeleteServlet", urlPatterns = {"/desarrolladorDeleteServlet"})
public class desarrolladorDeleteServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String nombre = request.getParameter("nombre");

        try (PrintWriter out = response.getWriter()) {
            // Conectar a Neo4j
            Neo4jConnection neo4j = new Neo4jConnection("bolt://localhost:7687", "neo4j", "12345678");
            try (Session session = neo4j.getSession()) {
                // Eliminar el nodo "Desarrollador" de la base de datos
                String eliminarDesarrollador = "MATCH (d:Desarrollador {nombre: $nombre}) DETACH DELETE d RETURN COUNT(d) > 0 AS deleted";
                Result result = session.run(eliminarDesarrollador, Map.of("nombre", nombre));

                // Confirmar la eliminación del desarrollador
                if (result.hasNext() && result.single().get("deleted").asBoolean()) {
                    out.println("<script>alert('Desarrollador eliminado exitosamente.'); window.location='CRUDDesarrollador.jsp';</script>");
                } else {
                    out.println("<script>alert('Error: No se encontró un desarrollador con ese nombre.'); window.history.back();</script>");
                }
            } catch (Exception e) {
                out.println("<script>alert('Error al eliminar el desarrollador: " + e.getMessage() + "'); window.history.back();</script>");
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
        return "Servlet para eliminar desarrolladores en Neo4j";
    }
}