<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Eliminar Todos los Nodos</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            font-size: 16px; /* Tamaño de fuente uniforme */
        }
        .form-container {
            width: 300px;
            margin: 50px auto;
            padding: 20px;
            background-color: #f0f0f0;
            border-radius: 10px;
            text-align: left;
        }
        h1 {
            text-align: center;
            font-size: 20px; /* Tamaño del título ajustado */
        }
        .button-container {
            margin-top: 10px;
        }
        input[type="submit"], .back-button {
            display: block;
            width: 100%;
            padding: 10px;
            background-color: #007BFF; /* Color del botón */
            color: white;
            text-decoration: none;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-align: center;
            box-sizing: border-box;
            font-size: 16px; /* Tamaño de fuente uniforme */
            margin-bottom: 15px; /* Espacio debajo del botón */
        }
        input[type="submit"]:hover, .back-button:hover {
            background-color: #0056b3; /* Color al pasar el mouse */
        }
    </style>
</head>
<body>

<div class="form-container">
    <h1>Eliminar Todos los Nodos</h1>
    <form action="deleteAllServlet" method="post">
        <div class="button-container">
            <input type="submit" value="Eliminar Todos los Nodos">
            <a href="index.jsp" class="back-button">Volver al Menú Principal</a>
        </div>
    </form>
</div>

</body>
</html>
