<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Eliminar Aplicación</title>
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
        label, input[type="text"], input[type="submit"], .back-button {
            font-size: 16px; /* Tamaño de fuente uniforme */
        }
        label {
            display: block;
            margin-bottom: 5px;
        }
        input[type="text"] {
            width: calc(100% - 16px); 
            padding: 8px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        .button-container {
            margin-top: 10px;
        }
        input[type="submit"], .back-button {
            display: block;
            width: 100%;
            padding: 10px;
            background-color: #007BFF;
            color: white;
            text-decoration: none;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-align: center;
            box-sizing: border-box;
            font-size: 16px; /* Tamaño de fuente uniforme */
        }
        input[type="submit"] {
            margin-bottom: 15px; 
        }
        input[type="submit"]:hover, .back-button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>

<div class="form-container">
    <h1>Eliminar Aplicación</h1>
    <form action="aplicacionDeleteServlet" method="post">
        <label for="titulo">Título:</label>
        <input type="text" id="titulo" name="titulo" required>

        <div class="button-container">
            <input type="submit" value="Eliminar Aplicación">
            <a href="CRUDAplicacion.jsp" class="back-button">Volver al Menú de Aplicación</a>
        </div>
    </form>
</div>

</body>
</html>