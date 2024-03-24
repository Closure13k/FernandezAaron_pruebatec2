<%@page import="com.closure13k.aaronfmpt2.logic.model.Procedure"%>
<%@page import="java.util.stream.Collectors"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Solicitud de Turno</title>
    </head>
    <body>
        <h1>Solicitud de Turno</h1>
        <form action="TurnServlet" method="POST">
            <!-- Campo DNI -->
            <label for="nif_turno">NIF</label>
            <input type="text" name="nif_turno" 
                   title="Formato: 8 Números seguidos de una letra" 
                   pattern="^[0-9]{8}[A-Za-z]{1}$" required/>


            <!-- Combo de Trámites -->
            <% if (request.getAttribute("tramites") != null) { %>
            <label for="tramite">Elige un trámite</label>
            <select name="tramite">
                <% List<Procedure> procList = (List<Procedure>) request.getAttribute("tramites");
                    String options = procList.stream()
                            .map(proc -> "<option value=\"" + proc.getId() + "\">" + proc.getDescription() + "</option>")
                            .collect(Collectors.joining());
                %>
                <%=options%>
            </select>
            <% }%>
            <!-- Ingreso -->
            <input type="submit" value="Confirmar Solicitud">
        </form>
    </body>
</html>