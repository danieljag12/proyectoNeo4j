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

@WebServlet(name = "ubicacionCreateServlet", urlPatterns = {"/ubicacionCreateServlet"})
public class ubicacionCreateServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String pais = request.getParameter("pais");

        try (PrintWriter out = response.getWriter()) {
            // Validar el nombre del país
            if (pais.isEmpty()) {
                out.println("<script>alert('Error: El nombre del país no puede estar vacío.'); window.history.back();</script>");
                return;
            }

            // Conectar a Neo4j
            Neo4jConnection neo4j = new Neo4jConnection("bolt://localhost:7687", "neo4j", "12345678");
            try (Session session = neo4j.getSession()) {
                // Crear un nodo "Ubicación" en la base de datos
                String crearUbicacion = "CREATE (u:Ubicación {pais: $pais}) RETURN u";
                Result result = session.run(crearUbicacion, Map.of("pais", pais));

                // Confirmar la creación de la ubicación
                if (result.hasNext()) {
                    String paisCreado = result.single().get(0).get("pais").asString();
                    // Mostrar alerta de éxito con los datos de la ubicación
                    out.println("<script>alert('Ubicación creada exitosamente: País: " + paisCreado + "'); window.location='CRUDUbicacion.jsp';</script>");
                } else {
                    out.println("<script>alert('Error: No se pudo crear la ubicación.'); window.history.back();</script>");
                }
            } catch (Exception e) {
                out.println("<script>alert('Error al crear la ubicación: " + e.getMessage() + "'); window.history.back();</script>");
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
        return "Servlet para crear ubicaciones en Neo4j";
    }
}