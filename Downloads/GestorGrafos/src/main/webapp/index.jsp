<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Menú Principal</title>
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
    <h2>Sistema gestor de grafos</h2>
    <a href="cargarDatos.jsp">Cargar datos</a>
    <a href="CRUDAplicacion.jsp">CRUD Aplicación</a>
    <a href="CRUDTecnologia.jsp">CRUD Tecnología</a>
    <a href="CRUDDesarrollador.jsp">CRUD Desarrollador</a>
    <a href="CRUDUbicacion.jsp">CRUD Ubicación</a>
    <a href="deleteAll.jsp">Limpiar Base de datos</a>
    <a href="menuConsultas.jsp">Menú de consultas</a>
</div>

</body>
</html>