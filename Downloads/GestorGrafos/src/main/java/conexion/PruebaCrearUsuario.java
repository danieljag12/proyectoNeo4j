package conexion;

import org.neo4j.driver.*;

public class PruebaCrearUsuario {
    public static void main(String[] args) {
        // Conectar a la base de datos por defecto "neo4j"
        Neo4jConnection neo4j = new Neo4jConnection("bolt://localhost:7687", "neo4j", "23102024"); // Cambia la contraseña si es diferente

        // Probar la conexión y crear un usuario
        try (Session session = neo4j.getSession()) {
            System.out.println("Conexión exitosa a Neo4j.");
            
            // Crear un nodo "Usuario" en la base de datos
            String crearUsuario = "CREATE (u:Usuario {nombre: 'Daniel', contrasena: '120197'}) RETURN u";
            Result result = session.run(crearUsuario);

            // Confirmar la creación del usuario
            System.out.println("Usuario creado exitosamente: " + result.single().get(0));
        } catch (Exception e) {
            System.err.println("Error al conectar a Neo4j o crear el usuario: " + e.getMessage());
        } finally {
            // Cerrar la conexión
            neo4j.close();
        }
    }
}
