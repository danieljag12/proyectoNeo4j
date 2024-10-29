<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Subir Archivo CSV</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            font-size: 16px;
            margin: 0;
            padding: 20px;
            background-color: #f4f4f4;
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
            font-size: 20px;
        }
        label {
            display: block;
            margin-bottom: 5px;
        }
        input[type="file"] {
            width: calc(100% - 16px);
            margin-bottom: 10px;
        }
        input[type="submit"], .back-button {
            display: block;
            width: 100%;
            padding: 10px;
            background-color: #007BFF;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-align: center;
            box-sizing: border-box;
            font-size: 16px;
            margin-bottom: 15px;
            text-decoration: none;
        }
        input[type="submit"]:hover, .back-button:hover {
            background-color: #0056b3;
        }
    </style>
    <script>
        // Mostrar un mensaje de alerta si hay un mensaje en el request
        window.onload = function() {
            var message = '<%= request.getAttribute("message") != null ? request.getAttribute("message") : "" %>';
            if (message) {
                alert(message);
            }
        };
    </script>
</head>
<body>

<div class="form-container">
    <h1>Subir Archivo CSV</h1>
    <form action="UploadCSVServlet" method="post" enctype="multipart/form-data">
        <label for="csvFile">Selecciona el archivo CSV:</label>
        <input type="file" id="csvFile" name="csvFile" accept=".csv" required>
        <input type="submit" value="Cargar CSV">
        <button type="button" class="back-button" onclick="window.location.href='index.jsp'">Volver al Menú Principal</button>
    </form>
</div>

</body>
</html>
