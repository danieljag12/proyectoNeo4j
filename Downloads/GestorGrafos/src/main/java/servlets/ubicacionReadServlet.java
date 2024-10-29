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
import java.util.List;

@WebServlet(name = "ubicacionReadServlet", urlPatterns = {"/ubicacionReadServlet"})
public class ubicacionReadServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Conectar a Neo4j
        Neo4jConnection neo4j = new Neo4jConnection("bolt://localhost:7687", "neo4j", "12345678");
        List<String> ubicaciones = new ArrayList<>();

        try (Session session = neo4j.getSession()) {
            // Obtener los nombres de los países de la base de datos
            String obtenerUbicaciones = "MATCH (u:Ubicación) RETURN u.pais";
            Result result = session.run(obtenerUbicaciones);

            while (result.hasNext()) {
                var record = result.next();
                String pais = record.get("u.pais").asString();
                ubicaciones.add(pais);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Cerrar la conexión
            neo4j.close();
        }

        // Establecer la lista de ubicaciones como atributo de la solicitud
        request.setAttribute("ubicaciones", ubicaciones);
        // Redirigir a la página JSP para mostrar las ubicaciones
        request.getRequestDispatcher("ubicacionRead.jsp").forward(request, response);
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
        return "Servlet para mostrar ubicaciones";
    }
}