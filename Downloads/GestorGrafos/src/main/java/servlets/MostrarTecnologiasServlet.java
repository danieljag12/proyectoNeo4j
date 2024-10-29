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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "MostrarTecnologiasServlet", urlPatterns = {"/MostrarTecnologiasServlet"})
public class MostrarTecnologiasServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Neo4jConnection neo4j = new Neo4jConnection("bolt://localhost:7687", "neo4j", "12345678");
        List<Map<String, String>> tecnologias = new ArrayList<>();

        try (Session session = neo4j.getSession()) {
            String obtenerTecnologias = "MATCH (t:Tecnología)<-[:USA]-(a:Aplicación) " +
                                        "WITH t, count(a) AS num_aplicaciones " +
                                        "WHERE num_aplicaciones > 10 " +
                                        "RETURN t.nombre AS Tecnologia, num_aplicaciones AS TotalAplicaciones " +
                                        "ORDER BY num_aplicaciones DESC " +
                                        "LIMIT 5";
            Result result = session.run(obtenerTecnologias);

            while (result.hasNext()) {
                var record = result.next();
                Map<String, String> tecnologia = new HashMap<>();
                tecnologia.put("Tecnologia", record.get("Tecnologia").asString());
                tecnologia.put("TotalAplicaciones", String.valueOf(record.get("TotalAplicaciones").asInt()));
                tecnologias.add(tecnologia);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            neo4j.close();
        }

        request.setAttribute("tecnologias", tecnologias);
        request.getRequestDispatcher("mostrarTecnologias.jsp").forward(request, response);
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
        return "Servlet para mostrar tecnologías y el número total de aplicaciones que las utilizan";
    }
}