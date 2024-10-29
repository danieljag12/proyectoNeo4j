<%@page import="java.util.Map"%>
<%@page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Contar Aplicaciones por Tecnología</title>
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
        table {
            width: 50%; /* Ancho de la tabla centrado */
            margin: 20px auto; /* Centrar la tabla horizontalmente */
            border-collapse: collapse;
            display: none; /* Oculta la tabla por defecto */
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

<h1>Contar Aplicaciones por Tecnología</h1>

<!-- Formulario para ingresar el nombre de la tecnología -->
<div class="form-container">
    <form action="contarAplicacionesServlet" method="post">
        <label for="tecnologiaNombre">Nombre de la Tecnología:</label>
        <input type="text" name="tecnologiaNombre" id="tecnologiaNombre" required>
        <button type="submit" class="load-button">Contar</button>
    </form>
</div>

<!-- Muestra los resultados de la búsqueda si existen -->
<%
    Map<String, Object> resultado = (Map<String, Object>) request.getAttribute("resultado");
    String tecnologiaNombre = (String) request.getAttribute("tecnologiaNombre");

    if (resultado != null) {
        int cantidadAplicaciones = (int) resultado.get("cantidad_de_aplicaciones");
%>
        <table style="display: table;"> <!-- La tabla se mostrará solo si hay resultados -->
            <thead>
                <tr>
                    <th>Tecnología</th>
                    <th>Cantidad de Aplicaciones</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td><%= tecnologiaNombre %></td>
                    <td><strong><%= cantidadAplicaciones %></strong></td>
                </tr>
            </tbody>
        </table>
<%
    } else {
        // No mostrar nada si no hay resultados
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