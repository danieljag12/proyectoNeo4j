package servlets;

import conexion.Neo4jConnection; // Asegúrate de tener esta clase para la conexión
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.neo4j.driver.Session;
import org.neo4j.driver.Result;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "aplicacionReadServlet", urlPatterns = {"/aplicacionReadServlet"})
public class aplicacionReadServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Conectar a Neo4j
        Neo4jConnection neo4j = new Neo4jConnection("bolt://localhost:7687", "neo4j", "12345678");
        List<Map<String, String>> aplicaciones = new ArrayList<>();

        try (Session session = neo4j.getSession()) {
            // Obtener los campos de la base de datos
            String obtenerAplicaciones = "MATCH (a:Aplicación) RETURN a.titulo AS titulo, a.subtitulo AS subtitulo, a.enlaceYouTube AS enlaceYouTube, a.funcionalidad AS funcionalidad, a.enlace AS enlace";
            Result result = session.run(obtenerAplicaciones);

            while (result.hasNext()) {
                var record = result.next();
                Map<String, String> aplicacion = new HashMap<>();
                aplicacion.put("titulo", record.get("titulo").asString());
                aplicacion.put("subtitulo", record.get("subtitulo").asString());
                aplicacion.put("enlaceYouTube", record.get("enlaceYouTube").asString());
                aplicacion.put("funcionalidad", record.get("funcionalidad").asString());
                aplicacion.put("enlace", record.get("enlace").asString());
                aplicaciones.add(aplicacion);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Cerrar la conexión
            neo4j.close();
        }

        // Establecer la lista de aplicaciones como atributo de la solicitud
        request.setAttribute("aplicaciones", aplicaciones);
        // Redirigir a la página JSP para mostrar las aplicaciones
        request.getRequestDispatcher("aplicacionRead.jsp").forward(request, response);
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
        return "Servlet para mostrar aplicaciones";
    }
}