<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Menú de consultas</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        .menu {
            width: 200px;
            margin: 50px auto;
            padding: 20px;
            background-color: #f0f0f0;
            border-radius: 10px;
            text-align: center;
        }
        .menu a {
            display: block;
            margin: 10px 0;
            padding: 10px;
            background-color: #007BFF;
            color: white;
            text-decoration: none;
            border-radius: 5px;
        }
        .menu a:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>

<div class="menu">
    <h2>Menú de consultas</h2>
    <a href="contarAplicaciones.jsp">Consulta 1</a>
    <a href="buscarAplicacionesSimilares.jsp">Consulta 2</a>
    <a href="buscarAplicaciones.jsp">Consulta 3</a>
    <a href="mostrarTecnologias.jsp">Consulta 4</a>
    <a href="contarDesarrolladores.jsp">Consulta 5</a>
    <a href="mostrarAplicaciones.jsp">Consulta 6</a>
    <a href="index.jsp">Menú principal</a>
</div>

</body>
</html>