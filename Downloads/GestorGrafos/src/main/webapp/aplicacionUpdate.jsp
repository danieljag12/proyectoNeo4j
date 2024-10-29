<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Actualizar Aplicación</title>
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
        input[type="text"],
        input[type="url"],
        textarea {
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
        <h1>Buscar Aplicación</h1>
        <form action="aplicacionUpdateServlet" method="get">
            <div class="form-group">
                <label for="titulo">Título de la Aplicación:</label>
                <input type="text" id="titulo" name="titulo" required />
            </div>
            <button type="submit" name="action" value="load">Buscar</button>
        </form>

        <c:if test="${not empty titulo}">
            <h1>Actualizar Aplicación: ${titulo}</h1>
            <form action="aplicacionUpdateServlet" method="post">
                <input type="hidden" name="titulo" value="${titulo}"/> <!-- Título oculto -->
                
                <div class="form-group">
                    <label for="subtitulo">Subtítulo:</label>
                    <input type="text" id="subtitulo" name="subtitulo" value="${subtitulo}" required/>
                </div>
                
                <div class="form-group">
                    <label for="funcionalidad">Funcionalidad:</label>
                    <textarea id="funcionalidad" name="funcionalidad" required>${funcionalidad}</textarea>
                </div>
                
                <div class="form-group">
                    <label for="enlaceYouTube">Enlace YouTube:</label>
                    <input type="url" id="enlaceYouTube" name="enlaceYouTube" value="${enlaceYouTube}" required/>
                </div>
                
                <div class="form-group">
                    <label for="enlace">Enlace:</label>
                    <input type="url" id="enlace" name="enlace" value="${enlace}" required/>
                </div>
                
                <button type="submit" name="action" value="update">Actualizar</button>
            </form>

            <a href="CRUDAplicacion.jsp" class="back-button">Volver al Menú de Aplicación</a>
        </c:if>
    </div>
</body>
</html>