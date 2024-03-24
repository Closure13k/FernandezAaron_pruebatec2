<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.util.stream.Collectors"%>
<%@page import="com.closure13k.aaronfmpt2.logic.model.Turn"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Turnos</title>
    </head>
    <body>
        <div class="container">
            <form id="filtroForm" action="TurnServlet" method="GET">
                <!-- Filtro fecha -->
                <label for="fecha">Fecha:</label>
                <input type="date" id="fecha" name="fecha" value="<%= (String) request.getAttribute("fecha")%>" required>
                <br>
                <!-- Filtro estado -->
                <label for="estado">Estado:</label>
                <select id="estado" name="estado">
                    <%
                        String status = (String) request.getAttribute("estado");
                        status = (status == null) ? "" : status;
                    %> 
                    <option value="" <%=status.equals("") ? "selected" : ""%>>Atendido/Pendiente</option>
                    <option value="false" <%=status.equals("false") ? "selected" : ""%>>Atendido</option>
                    <option value="true" <%=status.equals("true") ? "selected" : ""%>>Pendiente</option>
                </select>
                <br>
                <button type="submit">Filtrar</button>
            </form>
        </div>

        <% if (request.getAttribute("lista_turnos") != null) { %>
        <div class="panel">
            <table id="tablaTurnos">
                <thead>
                    <tr>
                        <th>Nº</th>
                        <th>Fecha</th>
                        <th>NIF del Ciudadano</th>
                        <th>Trámite</th>
                        <th>Estado</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<Turn> turns = (List<Turn>) request.getAttribute("lista_turnos");
                        String table = turns.stream()
                                .map(turn -> "<tr><td>" + turn.getId() + "</td>"
                                + "<td>" + turn.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy - HH:mm")) + "</td>"
                                + "<td>" + turn.getCitizen().getNif() + "</td>"
                                + "<td>" + turn.getProcedure().getDescription() + "</td>"
                                + "<td>" + (turn.isPending() ? "Pendiente" : "Atendido")
                                + "</td></tr>")
                                .collect(Collectors.joining());
                    %>
                    <%=table%>
                </tbody>
            </table>
        </div>
        <% }%>
    </body>
</html>