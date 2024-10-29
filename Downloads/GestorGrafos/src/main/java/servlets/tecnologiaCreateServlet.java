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

@WebServlet(name = "tecnologiaCreateServlet", urlPatterns = {"/tecnologiaCreateServlet"})
public class tecnologiaCreateServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String nombre = request.getParameter("nombre");

        try (PrintWriter out = response.getWriter()) {
            // Validar el nombre de la tecnología (ejemplo de validación simple)
            if (nombre == null || nombre.trim().isEmpty()) {
                out.println("<script>alert('Error: El nombre de la tecnología no puede estar vacío.'); window.history.back();</script>");
                return;
            }

            // Conectar a Neo4j
            Neo4jConnection neo4j = new Neo4jConnection("bolt://localhost:7687", "neo4j", "12345678");
            try (Session session = neo4j.getSession()) {
                // Crear un nodo "Tecnología" en la base de datos
                String crearTecnologia = "CREATE (t:Tecnología {nombre: $nombre}) RETURN t";
                Result result = session.run(crearTecnologia, Map.of("nombre", nombre));

                // Confirmar la creación de la tecnología
                if (result.hasNext()) {
                    String tecnologiaCreada = result.single().get("nombre").asString();
                    // Mostrar alerta de éxito con los datos de la tecnología
                    out.println("<script>alert('Tecnología creada exitosamente.'); window.location='CRUDTecnologia.jsp';</script>");
                } else {
                    out.println("<script>alert('Error: No se pudo crear la tecnología.'); window.history.back();</script>");
                }
            } catch (Exception e) {
                out.println("<script>alert('Error al crear la tecnología: " + e.getMessage() + "'); window.history.back();</script>");
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
        return "Servlet para crear tecnologías en Neo4j";
    }
}