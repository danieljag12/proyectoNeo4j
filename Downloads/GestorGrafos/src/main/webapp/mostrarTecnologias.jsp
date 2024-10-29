<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mostrar Tecnologías</title>
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
            margin: 0 10px; /* Espacio horizontal entre botones */
            display: inline-block; /* Asegura que se muestren en línea */
        }
        .load-button:hover, .back-button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>

<h1>Lista de Tecnologías y Número de Aplicaciones</h1>

<table>
    <thead>
    <tr>
        <th>Tecnología</th>
        <th>Total de Aplicaciones</th>
    </tr>
    </thead>
    <tbody>
    <%
        List<Map<String, String>> tecnologias = (List<Map<String, String>>) request.getAttribute("tecnologias");
        if (tecnologias != null && !tecnologias.isEmpty()) {
            for (Map<String, String> tecnologia : tecnologias) {
                %>
                <tr>
                    <td><%= tecnologia.get("Tecnologia") %></td>
                    <td><%= tecnologia.get("TotalAplicaciones") %></td>
                </tr>
                <%
            }
        } else {
            %>
            <tr>
                <td colspan="2">No hay tecnologías disponibles.</td>
            </tr>
            <%
        }
    %>
    </tbody>
</table>

<div class="button-container">
    <form action="MostrarTecnologiasServlet" method="post" style="display: inline;">
        <button type="submit" class="load-button">Cargar Tecnologías</button>
    </form>
    <a href="menuConsultas.jsp" class="back-button">Volver al Menú Consultas</a>
</div>

</body>
</html>