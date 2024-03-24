<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div class="container">
            <form id="turneroForm" action="ProcedureServlet" method="get">
                <input type="submit" value="Agregar Turno">
            </form>
            <form id="turneroForm" action="TurnServlet" method="get">
                <input type="submit" value="Listar Turno">
            </form>
        </div>
    </body>
</html>
