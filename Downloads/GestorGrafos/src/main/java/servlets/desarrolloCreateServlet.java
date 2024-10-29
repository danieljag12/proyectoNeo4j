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

@WebServlet(name = "desarrolloCreateServlet", urlPatterns = {"/desarrolloCreateServlet"})
public class desarrolloCreateServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String nombre = request.getParameter("nombre");

        try (PrintWriter out = response.getWriter()) {
            // Conectar a Neo4j
            Neo4jConnection neo4j = new Neo4jConnection("bolt://localhost:7687", "neo4j", "12345678");
            try (Session session = neo4j.getSession()) {
                // Crear un nodo "Desarrollador" en la base de datos
                String crearDesarrollador = "CREATE (d:Desarrollador {nombre: $nombre}) RETURN d";
                Result result = session.run(crearDesarrollador, Map.of("nombre", nombre));

                // Confirmar la creación del desarrollador
                if (result.hasNext()) {
                    String desarrolladorCreado = result.single().get("d").get("nombre").asString();
                    // Mostrar alerta de éxito con los datos del desarrollador
                    out.println("<script>alert('Desarrollador creado exitosamente: Nombre: " + desarrolladorCreado + "'); window.location='CRUDDesarrollador.jsp';</script>");
                } else {
                    out.println("<script>alert('Error: No se pudo crear el desarrollador.'); window.history.back();</script>");
                }
            } catch (Exception e) {
                out.println("<script>alert('Error al crear el desarrollador: " + e.getMessage() + "'); window.history.back();</script>");
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
        return "Servlet para crear desarrolladores en Neo4j";
    }
}