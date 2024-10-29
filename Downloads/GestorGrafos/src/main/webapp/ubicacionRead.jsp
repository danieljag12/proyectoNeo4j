<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mostrar Ubicaciones</title>
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
            display: flex;
            justify-content: center;
            gap: 20px;
            margin-top: 20px;
        }
        .load-button, .back-button {
            padding: 10px 15px;
            background-color: #007BFF;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            text-align: center;
            border: none;
            cursor: pointer;
            font-size: 16px;
            width: 200px;
        }
        .load-button:hover, .back-button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>

<h1>Lista de Ubicaciones</h1>

<table>
    <thead>
    <tr>
        <th>País</th>
    </tr>
    </thead>
    <tbody>
    <%
        // Obtener la lista de ubicaciones desde el servlet
        List<String> ubicaciones = (List<String>) request.getAttribute("ubicaciones");
        if (ubicaciones != null && !ubicaciones.isEmpty()) {
            for (String pais : ubicaciones) {
                %>
                <tr>
                    <td><%= pais %></td>
                </tr>
                <%
            }
        } else {
            %>
            <tr>
                <td colspan="1">No hay ubicaciones disponibles.</td>
            </tr>
            <%
        }
    %>
    </tbody>
</table>

<div class="button-container">
    <form action="ubicacionReadServlet" method="post">
        <button type="submit" class="load-button">Cargar Ubicaciones</button>
    </form>
    <a href="CRUDUbicacion.jsp" class="back-button">Volver al Menú de Ubicación</a>
</div>

</body>
</html>