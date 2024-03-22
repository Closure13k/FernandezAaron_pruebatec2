<%@page import="java.util.stream.Collectors"%>
<%@page import="com.closure13k.aaronfmpt2.logic.Procedure"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <% if (request.getAttribute("resultados") != null) { %>
        <h3>Resultados:</h3>
        <label for="tramites">Elige un trámite</label>
        <select id="tramites">
            <% List<Procedure> procList = (List<Procedure>) request.getAttribute("resultados");
                String options = procList.stream()
                        .map(proc -> "<option value=" + proc.getId() + ">" + proc.getDescription() + "</option>")
                        .collect(Collectors.joining());
            %>
            <%=options%>
        </select>
        <% } else {%>
        NO HAY NADA
        <% }%>
    </body>
</html>
