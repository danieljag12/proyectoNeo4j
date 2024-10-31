<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Buscar Desarrolladores por Tecnologías</title>
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
            display: none;
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
    </style>
</head>
<body>

<h1>Buscar Desarrolladores por Tecnologías</h1>

<!-- Formulario para ingresar los nombres de las tecnologías -->
<div class="form-container">
    <form action="buscarDesarrolladoresServlet" method="post">
        <label for="tecnologia1">Nombre de la Tecnología 1:</label>
        <input type="text" name="tecnologia1" id="tecnologia1" required>
        <br><br>
        <label for="tecnologia2">Nombre de la Tecnología 2:</label>
        <input type="text" name="tecnologia2" id="tecnologia2" required>
        <br><br>
        <button type="submit" class="load-button">Buscar</button>
    </form>
</div>

<!-- Muestra los resultados de la búsqueda si existen -->
<%
    Map<String, Object> resultado = (Map<String, Object>) request.getAttribute("resultado");
    String tecnologia1 = (String) request.getAttribute("tecnologia1");
    String tecnologia2 = (String) request.getAttribute("tecnologia2");

    if (resultado != null) {
        List<Map<String, String>> desarrolladores = (List<Map<String, String>>) resultado.get("desarrolladores");
%>
        <table style="display: table;">
            <thead>
                <tr>
                    <th>Desarrollador 1</th>
                    <th>Desarrollador 2</th>
                </tr>
            </thead>
            <tbody>
<%
        for (Map<String, String> pair : desarrolladores) {
%>
                <tr>
                    <td><%= pair.get("Desarrollador1") %></td>
                    <td><%= pair.get("Desarrollador2") %></td>
                </tr>
<%
        }
%>
            </tbody>
        </table>
<%
    } else {
%>
        <div id="no-results-message" style="display: none;"></div>
<%
    }
%>

<div class="button-container">
    <a href="menuConsultas.jsp" class="back-button">Volver al Menú Consultas</a>
</div>

</body>
</html>
