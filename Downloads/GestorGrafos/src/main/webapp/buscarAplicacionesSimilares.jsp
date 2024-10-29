<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Buscar Aplicaciones Similares</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f4f4f4;
            font-size: 16px; /* Tamaño de fuente uniforme para todo el cuerpo */
        }
        h1 {
            text-align: center;
            font-size: 1.5em; /* Se puede ajustar según sea necesario */
        }
        .form-container {
            text-align: center;
            margin: 20px;
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
            font-size: 16px; /* Tamaño de fuente uniforme para botones */
            margin: 0 10px; /* Espacio horizontal entre botones */
            display: inline-block; /* Asegura que se muestren en línea */
        }
        .load-button:hover, .back-button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>

<h1>Buscar Aplicaciones Similares</h1>

<!-- Formulario para ingresar el nombre de la aplicación -->
<div class="form-container">
    <form action="buscarAplicacionesSimilaresServlet" method="post">
        <label for="applicationName">Nombre de la Aplicación:</label>
        <input type="text" name="applicationName" id="applicationName" required>
        <button type="submit" class="load-button">Buscar</button>
    </form>
</div>

<!-- Muestra los resultados de la búsqueda si existen -->
<%
    List<Map<String, Object>> aplicacionesSimilares = (List<Map<String, Object>>) request.getAttribute("aplicacionesSimilares");
    if (aplicacionesSimilares != null) {
%>
    <table>
        <thead>
            <tr>
                <th>Aplicaciones Similares</th>
                <th>Tecnologías Compartidas</th>
            </tr>
        </thead>
        <tbody>
            <%
                if (!aplicacionesSimilares.isEmpty()) {
                    for (Map<String, Object> aplicacion : aplicacionesSimilares) {
            %>
                <tr>
                    <td><%= aplicacion.get("aplicaciones_similares") %></td>
                    <td><%= aplicacion.get("tecnologias_compartidas") %></td>
                </tr>
            <%
                    }
                } else {
            %>
                <tr>
                    <td colspan="2">No se encontraron aplicaciones similares.</td>
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