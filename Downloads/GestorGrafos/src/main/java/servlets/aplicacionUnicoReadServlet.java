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
@WebServlet(name = "aplicacionUnicoReadServlet", urlPatterns = {"/aplicacionUnicoReadServlet"})
public class aplicacionUnicoReadServlet extends HttpServlet {

    private final String NEO4J_URI = "bolt://localhost:7687"; // Cambia esto según tu configuración
    private final String NEO4J_USER = "neo4j"; // Cambia esto según tu configuración
    private final String NEO4J_PASSWORD = "12345678"; // Cambia esto según tu configuración

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nombreAplicacion = request.getParameter("nombreAplicacion");
        Map<String, Object> resultado = new HashMap<>();

        // Conectar a Neo4j
        try (Session session = GraphDatabase.driver(NEO4J_URI, AuthTokens.basic(NEO4J_USER, NEO4J_PASSWORD)).session()) {
            // Consulta para obtener la aplicación
            String query = "MATCH (a:Aplicación {titulo: $titulo}) " +
                           "RETURN a.titulo AS titulo, a.subtitulo AS subtitulo, a.enlaceYouTube AS enlaceYouTube, " +
                           "a.funcionalidad AS funcionalidad, a.enlace AS enlace";

            // Ejecutar la consulta
            Result result = session.run(query, org.neo4j.driver.Values.parameters("titulo", nombreAplicacion));

            // Procesar el resultado
            if (result.hasNext()) {
                var record = result.next();
                resultado.put("titulo", record.get("titulo").asString());
                resultado.put("subtitulo", record.get("subtitulo").asString());
                resultado.put("enlaceYouTube", record.get("enlaceYouTube").asString());
                resultado.put("funcionalidad", record.get("funcionalidad").asString());
                resultado.put("enlace", record.get("enlace").asString());
            } else {
                resultado = null; // No hay resultados
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al conectar a la base de datos.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        // Almacenar el resultado en el request y redirigir al JSP
        request.setAttribute("resultado", resultado);
        request.setAttribute("nombreAplicacion", nombreAplicacion);
        request.getRequestDispatcher("aplicacionUnicoRead.jsp").forward(request, response);
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
        return "Servlet para leer una aplicación única";
    }
}