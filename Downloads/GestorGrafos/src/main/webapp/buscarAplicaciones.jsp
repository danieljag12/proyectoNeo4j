<%@page import="java.util.List"%>
<%@page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Buscar Aplicaciones por Desarrollador</title>
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

<h1>Buscar Aplicaciones por Desarrollador</h1>

<!-- Formulario para ingresar el nombre del desarrollador -->
<div class="form-container">
    <form action="BuscarAplicacionesServlet" method="post">
        <label for="developerName">Nombre del Desarrollador:</label>
        <input type="text" name="developerName" id="developerName" required>
        <button type="submit" class="load-button">Buscar</button>
    </form>
</div>

<!-- Muestra los resultados de la búsqueda si existen -->
<%
    List<String> aplicacionesCreadas = (List<String>) request.getAttribute("aplicacionesCreadas");
    if (aplicacionesCreadas != null) {
%>
    <table>
        <thead>
            <tr>
                <th>Aplicaciones Creadas</th>
            </tr>
        </thead>
        <tbody>
            <%
                if (!aplicacionesCreadas.isEmpty()) {
                    for (String aplicacion : aplicacionesCreadas) {
            %>
                <tr>
                    <td><%= aplicacion %></td>
                </tr>
            <%
                    }
                } else {
            %>
                <tr>
                    <td colspan="1">No se encontraron aplicaciones para este desarrollador.</td>
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