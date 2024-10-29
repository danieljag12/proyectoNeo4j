<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Actualizar Desarrollador</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
        }
        .container {
            max-width: 600px;
            margin: auto;
            padding: 20px;
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h1 {
            text-align: center;
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
        }
        input[type="text"] {
            width: 100%;
            padding: 8px;
            box-sizing: border-box;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        button, .back-button {
            background-color: #007BFF; /* Color de fondo del botón */
            color: white; /* Color del texto */
            border: none; /* Sin borde */
            padding: 10px; /* Espaciado interno */
            cursor: pointer; /* Cambiar cursor al pasar el ratón */
            border-radius: 5px; /* Bordes redondeados */
            width: 100%; /* Ancho completo */
            text-align: center; /* Centrar texto */
            text-decoration: none; /* Sin subrayado para los enlaces */
            display: inline-block; /* Permitir que se comporten como botones */
            margin-top: 10px; /* Margen superior para separación */
            box-sizing: border-box; /* Asegura que el padding se incluya en el ancho */
        }
        button:hover, .back-button:hover {
            background-color: #0056b3; /* Color de fondo al pasar el ratón */
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Buscar Desarrollador</h1>
        <form action="desarrolladorUpdateServlet" method="get">
            <div class="form-group">
                <label for="nombre">Nombre del Desarrollador:</label>
                <input type="text" id="nombre" name="nombre" required />
            </div>
            <button type="submit" name="action" value="load">Buscar</button>
        </form>

        <c:if test="${not empty nombre}">
            <h1>Actualizar Desarrollador: ${nombre}</h1>
            <form action="desarrolladorUpdateServlet" method="post">
                <input type="hidden" name="nombre" value="${nombre}"/> <!-- Nombre oculto -->
                
                <div class="form-group">
                    <label for="nuevoNombre">Nuevo Nombre:</label>
                    <input type="text" id="nuevoNombre" name="nuevoNombre" value="${nuevoNombre}" required/>
                </div>
                
                <button type="submit" name="action" value="update">Actualizar</button>
            </form>

            <a href="CRUDDesarrollador.jsp" class="back-button">Volver al Menú de Desarrollador</a>
        </c:if>
    </div>
</body>
</html>
