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

@WebServlet(name = "deleteAllServlet", urlPatterns = {"/deleteAllServlet"})
public class deleteAllServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            // Conectar a Neo4j
            Neo4jConnection neo4j = new Neo4jConnection("bolt://localhost:7687", "neo4j", "12345678");
            try (Session session = neo4j.getSession()) {
                // Eliminar todos los nodos de la base de datos
                String eliminarTodos = "MATCH (n) DETACH DELETE n";
                session.run(eliminarTodos);

                // Confirmar la eliminación de todos los nodos
                out.println("<script>alert('Todos los nodos eliminados exitosamente.'); window.location='index.jsp';</script>");
            } catch (Exception e) {
                out.println("<script>alert('Error al eliminar los nodos: " + e.getMessage() + "'); window.history.back();</script>");
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
        return "Servlet para eliminar todos los nodos en Neo4j";
    }
}
