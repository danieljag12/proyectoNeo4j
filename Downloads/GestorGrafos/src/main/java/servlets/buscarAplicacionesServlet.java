package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;
import org.neo4j.driver.Result;

@WebServlet(name = "BuscarAplicacionesServlet", urlPatterns = {"/BuscarAplicacionesServlet"})
public class buscarAplicacionesServlet extends HttpServlet {
    private final Driver driver;

    public buscarAplicacionesServlet() {
        // Conecta con tu base de datos de Neo4j (ajusta las credenciales según sea necesario)
        driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "12345678"));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nombreDesarrollador = request.getParameter("developerName");
        List<String> aplicacionesCreadas = new ArrayList<>();

        // Realizamos la consulta en Neo4j
        try (Session session = driver.session()) {
            String query = "MATCH (a:Aplicación)-[:CREADA_POR]->(d:Desarrollador {nombre: $nombre}) RETURN a.titulo AS aplicaciones_creadas";
            Result result = session.run(query, org.neo4j.driver.Values.parameters("nombre", nombreDesarrollador));

            while (result.hasNext()) {
                aplicacionesCreadas.add(result.next().get("aplicaciones_creadas").asString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Pasamos la lista de aplicaciones al JSP
        request.setAttribute("aplicacionesCreadas", aplicacionesCreadas);
        RequestDispatcher dispatcher = request.getRequestDispatcher("buscarAplicaciones.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public void destroy() {
        driver.close();
    }
}