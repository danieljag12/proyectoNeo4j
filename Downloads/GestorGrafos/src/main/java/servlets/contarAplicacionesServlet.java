/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;

/**
 *
 * @author danie
 */
@WebServlet(name = "contarAplicacionesServlet", urlPatterns = {"/contarAplicacionesServlet"})
public class contarAplicacionesServlet extends HttpServlet {

    private final String NEO4J_URI = "bolt://localhost:7687"; // Cambia esto según tu configuración
    private final String NEO4J_USER = "neo4j"; // Cambia esto según tu configuración
    private final String NEO4J_PASSWORD = "12345678"; // Cambia esto según tu configuración

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String tecnologiaNombre = request.getParameter("tecnologiaNombre");
        Map<String, Object> resultado = new HashMap<>();

        // Conectar a Neo4j
        try (Session session = GraphDatabase.driver(NEO4J_URI, AuthTokens.basic(NEO4J_USER, NEO4J_PASSWORD)).session()) {
            // Consulta para contar aplicaciones
            String query = "MATCH (t:Tecnología {nombre: $nombre})<-[:USA]-(a:Aplicación) " +
                           "RETURN count(a) AS cantidad_de_aplicaciones";

            // Ejecutar la consulta
            Result result = session.run(query, org.neo4j.driver.Values.parameters("nombre", tecnologiaNombre));

            // Procesar el resultado
            if (result.hasNext()) {
                var record = result.next();
                resultado.put("cantidad_de_aplicaciones", record.get("cantidad_de_aplicaciones").asInt());
            } else {
                resultado.put("cantidad_de_aplicaciones", 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al conectar a la base de datos.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        // Almacenar el resultado en el request y redirigir al JSP
        request.setAttribute("resultado", resultado);
        request.setAttribute("tecnologiaNombre", tecnologiaNombre);
        request.getRequestDispatcher("contarAplicaciones.jsp").forward(request, response);
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
        return "Servlet para contar aplicaciones por tecnología";
    }
}