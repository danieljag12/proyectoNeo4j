<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Menú CRUD Tecnología</title>
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
    <h2>Menú CRUD Tecnología</h2>
    <a href="tecnologiaCreate.jsp">Crear un Nuevo Dato</a>
    <a href="tecnologiaRead.jsp">Leer Datos</a>
     <a href="tecnologiaUnicoRead.jsp">Leer un Unico Dato</a>
    <a href="tecnologiaUpdate.jsp">Modificar un Dato</a>
    <a href="tecnologiaDelete.jsp">Eliminar un Dato</a>
    <a href="index.jsp">Menú Principal</a>
</div>

</body>
</html>