package conexion;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.neo4j.driver.Session;

@WebListener
public class Main implements ServletContextListener {
    private Neo4jConnection neo4j;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            // Iniciar la conexión a Neo4j
            neo4j = new Neo4jConnection("bolt://localhost:7687", "neo4j", "23102024");
            sce.getServletContext().setAttribute("neo4jConnection", neo4j);
            System.out.println("Conexión a Neo4j inicializada correctamente.");
        } catch (Exception e) {
            System.err.println("Error al inicializar la conexión con Neo4j: " + e.getMessage());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (neo4j != null) {
            neo4j.close();
            System.out.println("Conexión a Neo4j cerrada.");
        }
    }
}
