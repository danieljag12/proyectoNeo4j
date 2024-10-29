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
import java.util.List;

@WebServlet(name = "desarrolladorReadServlet", urlPatterns = {"/desarrolladorReadServlet"})
public class desarrolladorReadServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Neo4jConnection neo4j = new Neo4jConnection("bolt://localhost:7687", "neo4j", "12345678");
        List<String> desarrolladores = new ArrayList<>();

        try (Session session = neo4j.getSession()) {
            String query = "MATCH (d:Desarrollador) RETURN d.nombre AS nombre";
            Result result = session.run(query);

            while (result.hasNext()) {
                var record = result.next();
                desarrolladores.add(record.get("nombre").asString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            neo4j.close();
        }

        request.setAttribute("desarrolladores", desarrolladores);
        request.getRequestDispatcher("desarrolladorRead.jsp").forward(request, response);
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
        return "Servlet para mostrar nombres de desarrolladores";
    }
}