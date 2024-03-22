<%@page import="java.util.stream.Collectors"%>
<%@page import="java.util.List"%>
<%@page import="com.closure13k.aaronfmpt2.logic.Procedure"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>

        <form id="turneroForm" action="TurneroServlet" method="get">
            <input type="submit" value="Enviar">
        </form>
    </body>
</html>
