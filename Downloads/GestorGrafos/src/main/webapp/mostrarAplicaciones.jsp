<%@page import="java.util.Map"%>
<%@page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mostrar Aplicaciones por Región</title>
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

<h1>Mostrar Aplicaciones por Región</h1>

<div class="form-container">
    <form action="MostrarAplicacionesServlet" method="post">
        <label for="regionNombre">Nombre de la Región:</label>
        <input type="text" name="regionNombre" id="regionNombre" required>
        <button type="submit" class="load-button">Mostrar</button>
    </form>
</div>

<%
    Map<String, Object> resultado = (Map<String, Object>) request.getAttribute("resultado");
    String regionNombre = (String) request.getAttribute("regionNombre");

    if (resultado != null) {
        java.util.List<Map<String, String>> aplicaciones = (java.util.List<Map<String, String>>) resultado.get("aplicaciones");
%>
        <table style="display: table;">
            <thead>
                <tr>
                    <th>Aplicación</th>
                    <th>Desarrollador</th>
                </tr>
            </thead>
            <tbody>
                <%
                    for (Map<String, String> app : aplicaciones) {
                %>
                <tr>
                    <td><%= app.get("Aplicación") %></td>
                    <td><%= app.get("Desarrollador") %></td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
<%
    }
%>

<div class="button-container">
    <a href="menuConsultas.jsp" class="back-button">Volver al Menú Consultas</a>
</div>

</body>
</html>
