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

@WebServlet(name = "tecnologiaDeleteServlet", urlPatterns = {"/tecnologiaDeleteServlet"})
public class tecnologiaDeleteServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String nombre = request.getParameter("nombre");

        try (PrintWriter out = response.getWriter()) {
            // Conectar a Neo4j
            Neo4jConnection neo4j = new Neo4jConnection("bolt://localhost:7687", "neo4j", "12345678");
            try (Session session = neo4j.getSession()) {
                // Eliminar el nodo "Tecnología" de la base de datos
                String eliminarTecnologia = "MATCH (t:Tecnología {nombre: $nombre}) DETACH DELETE t RETURN COUNT(t) > 0 AS deleted";
                Result result = session.run(eliminarTecnologia, Map.of("nombre", nombre));

                // Confirmar la eliminación de la tecnología
                if (result.hasNext() && result.single().get("deleted").asBoolean()) {
                    out.println("<script>alert('Tecnología eliminada exitosamente.'); window.location='CRUDTecnologia.jsp';</script>");
                } else {
                    out.println("<script>alert('Error: No se encontró una tecnología con ese nombre.'); window.history.back();</script>");
                }
            } catch (Exception e) {
                out.println("<script>alert('Error al eliminar la tecnología: " + e.getMessage() + "'); window.history.back();</script>");
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
        return "Servlet para eliminar tecnologías en Neo4j";
    }
}