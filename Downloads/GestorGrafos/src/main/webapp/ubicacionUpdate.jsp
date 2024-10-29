<%-- 
    Document   : ubicacionUpdate
    Created on : 26 oct 2024, 3:14:04 p. m.
    Author     : danie
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Actualizar Ubicación</title>
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
        button {
            background-color: #007BFF; /* Color azul */
            color: white;
            border: none;
            padding: 10px;
            cursor: pointer;
            border-radius: 5px;
            width: 100%;
            text-align: center;
            display: inline-block;
            margin-top: 10px;
            box-sizing: border-box;
        }
        button:hover {
            background-color: #0056b3; /* Color azul más oscuro al pasar el mouse */
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Buscar Ubicación</h1>
        <form action="ubicacionUpdateServlet" method="get">
            <div class="form-group">
                <label for="pais">Nombre del País:</label>
                <input type="text" id="pais" name="pais" required />
            </div>
            <button type="submit" name="action" value="load">Buscar</button>
        </form>

        <c:if test="${not empty pais}">
            <h1>Actualizar Ubicación: ${pais}</h1>
            <form action="ubicacionUpdateServlet" method="post">
                <input type="hidden" name="pais" value="${pais}"/> <!-- Nombre oculto -->
                
                <div class="form-group">
                    <label for="nuevoPais">Nuevo Nombre del País:</label>
                    <input type="text" id="nuevoPais" name="nuevoPais" value="${nuevoPais}" required/>
                </div>
                
                <button type="submit" name="action" value="update">Actualizar</button>
            </form>

            <form action="CRUDUbicacion.jsp" method="get">
                <button type="submit" class="back-button">Volver al Menú de Ubicación</button>
            </form>
        </c:if>
    </div>
</body>
</html>
