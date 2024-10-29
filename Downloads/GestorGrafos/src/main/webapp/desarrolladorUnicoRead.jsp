<%@page import="java.util.Map"%>
<%@page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Leer Desarrollador Único</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f4f4f4;
            font-size: 16px;
        }
        h1 {
            text-align: center;
            font-size: 1.5em;
        }
        .form-container {
            text-align: center;
            margin: 20px;
        }
        .button-container {
            text-align: center;
            margin-top: 20px;
        }
        .load-button, .back-button {
            padding: 10px 15px;
            background-color: #007BFF;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            width: 200px;
            border: none;
            cursor: pointer;
            font-size: 16px;
            margin: 0 10px;
            display: inline-block;
        }
        .load-button:hover, .back-button:hover {
            background-color: #0056b3;
        }
        table {
            width: 50%;
            margin: 20px auto;
            border-collapse: collapse;
            display: none; /* Inicialmente oculta */
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
        .no-results {
            text-align: center;
            margin-top: 20px;
            display: none; /* Oculto inicialmente */
        }
    </style>
</head>
<body>

<h1>Leer Desarrollador Único</h1>

<div class="form-container">
    <form action="desarrolladorUnicoReadServlet" method="post">
        <label for="nombreDesarrollador">Nombre del Desarrollador:</label>
        <input type="text" name="nombreDesarrollador" id="nombreDesarrollador" required>
        <button type="submit" class="load-button">Leer</button>
    </form>
</div>

<%
    String nombreDesarrollador = (String) request.getAttribute("nombreDesarrollador");
    String nombre = (String) request.getAttribute("nombre");
    
    // Si hay un nombre, mostramos la tabla
    if (nombre != null) {
%>
        <table style="display: table;">
            <thead>
                <tr>
                    <th>Nombre</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td><%= nombre %></td>
                </tr>
            </tbody>
        </table>
<%
    } else if (nombreDesarrollador != null) { // Solo muestra el mensaje si se ha buscado un desarrollador
%>
        <div class="no-results">No se encontraron resultados.</div>
<%
        // Mostrar el mensaje de "no resultados" si no hay coincidencias
        // Asegúrate de que el mensaje esté visible.
%>
        <script>
            document.querySelector('.no-results').style.display = 'block';
        </script>
<%
    }
%>

<div class="button-container">
    <a href="CRUDDesarrollador.jsp" class="back-button">Volver al Menú de Desarrollador</a>
</div>

</body>
</html>