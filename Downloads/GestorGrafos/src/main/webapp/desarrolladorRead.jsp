<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Desarrolladores</title>
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
        .button-group {
            display: inline-flex;
            gap: 10px;
        }
        .load-button, .back-button {
            padding: 10px 15px;
            background-color: #007BFF;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            border: none;
            cursor: pointer;
            font-size: 16px;
        }
        .load-button:hover, .back-button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>

<h1>Lista de Desarrolladores</h1>

<table>
    <thead>
    <tr>
        <th>Nombre</th>
    </tr>
    </thead>
    <tbody>
    <%
        List<String> desarrolladores = (List<String>) request.getAttribute("desarrolladores");
        if (desarrolladores != null && !desarrolladores.isEmpty()) {
            for (String nombre : desarrolladores) {
                %>
                <tr>
                    <td><%= nombre %></td>
                </tr>
                <%
            }
        } else {
            %>
            <tr>
                <td colspan="1">No hay desarrolladores disponibles.</td>
            </tr>
            <%
        }
    %>
    </tbody>
</table>

<div class="button-container">
    <div class="button-group">
        <form action="desarrolladorReadServlet" method="post">
            <button type="submit" class="load-button">Cargar Desarrolladores</button>
        </form>
        <a href="CRUDDesarrollador.jsp" class="back-button">Volver al Menú de Desarrollador</a>
    </div>
</div>

</body>
</html>