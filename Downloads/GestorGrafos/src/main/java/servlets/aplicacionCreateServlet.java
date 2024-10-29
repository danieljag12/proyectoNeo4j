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

@WebServlet(name = "aplicacionCreateServlet", urlPatterns = {"/aplicacionCreateServlet"})
public class aplicacionCreateServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        // Obtener parámetros del formulario
        String titulo = request.getParameter("titulo");
        String subtitulo = request.getParameter("subtitulo");
        String enlaceYouTube = request.getParameter("enlaceYouTube");
        String funcionalidad = request.getParameter("funcionalidad");
        String enlace = request.getParameter("enlace");

        try (PrintWriter out = response.getWriter()) {
            // Conectar a Neo4j
            Neo4jConnection neo4j = new Neo4jConnection("bolt://localhost:7687", "neo4j", "12345678");
            try (Session session = neo4j.getSession()) {
                // Crear un nodo "Aplicación" en la base de datos
                String crearAplicacion = "CREATE (a:Aplicación {titulo: $titulo, subtitulo: $subtitulo, enlaceYouTube: $enlaceYouTube, funcionalidad: $funcionalidad, enlace: $enlace}) RETURN a";
                Result result = session.run(crearAplicacion, Map.of(
                        "titulo", titulo,
                        "subtitulo", subtitulo,
                        "enlaceYouTube", enlaceYouTube,
                        "funcionalidad", funcionalidad,
                        "enlace", enlace
                ));

                // Confirmar la creación de la aplicación
                if (result.hasNext()) {
                    String appCreada = result.single().get("titulo").asString();
                    // Mostrar alerta de éxito con los datos de la aplicación
                    out.println("<script>alert('Aplicación creada exitosamente'); window.location='CRUDAplicacion.jsp';</script>");
                } else {
                    out.println("<script>alert('Error: No se pudo crear la aplicación.'); window.history.back();</script>");
                }
            } catch (Exception e) {
                out.println("<script>alert('Error al crear la aplicación: " + e.getMessage() + "'); window.history.back();</script>");
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
        return "Servlet para crear aplicaciones en Neo4j";
    }
}