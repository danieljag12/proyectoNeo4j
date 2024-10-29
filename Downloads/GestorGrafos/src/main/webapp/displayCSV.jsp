<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Contenido del CSV</title>
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
        .error {
            color: red;
            text-align: center;
            font-weight: bold;
            margin-bottom: 20px;
        }
    </style>
    <script>
        // Mostrar el mensaje de alerta si está presente
        <%
            String message = (String) request.getAttribute("message");
            if (message != null) {
        %>
            alert("<%= message %>");
        <%
            }
        %>
    </script>
</head>
<body>

<h1>Resultado de la Carga del Archivo CSV</h1>

<!-- Mostrar el mensaje de error si está presente -->
<%
    String error = (String) request.getAttribute("error");
    if (error != null) {
%>
    <div class="error"><%= error %></div>
<%
    } else {
        // Obtener y mostrar los datos del CSV
        List<String[]> csvData = (List<String[]>) request.getAttribute("csvData");
        if (csvData != null && !csvData.isEmpty()) {
%>
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
                    for (String[] row : csvData) {
                %>
                    <tr>
                        <td><%= row[0] %></td>
                        <td><%= row[1] %></td>
                        <td><a href="<%= row[2] %>">Ver Video</a></td>
                        <td><%= row[3] %></td>
                        <td><a href="<%= row[4] %>">Ir a Aplicación</a></td>
                    </tr>
                <%
                    }
                %>
                </tbody>
            </table>
<%
        } else {
%>
            <div class="error">No hay datos disponibles para mostrar.</div>
<%
        }
    }
%>

<!-- Contenedor para los botones al final de la página -->
<div class="button-container">

    <!-- Botón para volver al menú principal -->
    <a href="index.jsp" class="back-button">Volver al Menú Principal</a>
</div>

</body>
</html>
