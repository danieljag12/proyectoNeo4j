<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mostrar Aplicaciones</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f4f4f4;
        }
        h1 {
            text-align: center;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        table, th, td {
            border: 1px solid #ccc;
        }
        th, td {
            padding: 10px;
            text-align: left;
        }
        th {
            background-color: #007BFF;
            color: white;
        }
        /* Estilos para los botones */
        .button-container {
            text-align: center;
            margin-top: 20px;
        }
        .button-container form, .button-container a {
            display: inline-block;
            margin: 0 10px;
        }
        .load-button, .back-button {
            padding: 10px 15px;
            background-color: #007BFF;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            text-align: center;
            width: 200px;
            border: none;
            cursor: pointer;
            font-size: 16px; /* Tamaño de letra consistente */
        }
        .load-button:hover, .back-button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>

<h1>Lista de Aplicaciones</h1>

<!-- Mostrar las aplicaciones si existen -->
<table>
    <thead>
    <tr>
        <th>Título</th>
        <th>Subtítulo</th>
        <th>Enlace YouTube</th>
        <th>Funcionalidad</th>
        <th>Enlace</th>
    </tr>
    </thead>
    <tbody>
    <%
        // Obtener la lista de aplicaciones desde el servlet
        List<Map<String, String>> aplicaciones = (List<Map<String, String>>) request.getAttribute("aplicaciones");
        if (aplicaciones != null && !aplicaciones.isEmpty()) {
            for (Map<String, String> aplicacion : aplicaciones) {
                %>
                <tr>
                    <td><%= aplicacion.get("titulo") %></td>
                    <td><%= aplicacion.get("subtitulo") %></td>
                    <td><a href="<%= aplicacion.get("enlaceYouTube") %>">Ver Video</a></td>
                    <td><%= aplicacion.get("funcionalidad") %></td>
                    <td><a href="<%= aplicacion.get("enlace") %>">Ir a Aplicación</a></td>
                </tr>
                <%
            }
        } else {
            %>
            <tr>
                <td colspan="5">No hay aplicaciones disponibles.</td>
            </tr>
            <%
        }
    %>
    </tbody>
</table>

<!-- Contenedor para los botones al final de la página -->
<div class="button-container">
    <!-- Formulario con botón para cargar los datos -->
    <form action="aplicacionReadServlet" method="post">
        <button type="submit" class="load-button">Cargar Aplicaciones</button>
    </form>

    <!-- Botón para volver al menú principal -->
    <a href="CRUDAplicacion.jsp" class="back-button">Volver al Menú de Aplicación</a>
</div>

</body>
</html>