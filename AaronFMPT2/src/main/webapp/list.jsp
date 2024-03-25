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
        <style><%@include file="res/styles.css"%></style>
    </head>
    <body>
        <div class="container">
            <form id="turneroForm" action="ProcedureServlet" method="get">
                <div class="button-container">
                    <button type="submit">Agregar turno</button>
                </div>
            </form>
            
            
            <form id="filtroForm" action="TurnServlet" method="GET">
                <!-- Filtro fecha -->
                <div class="form-group">
                    <label for="fecha">Fecha:</label>
                    <input type="date" id="fecha" name="fecha" value="<%= (String) request.getAttribute("fecha")%>" required>
                </div>
                <!-- Filtro estado -->
                <div class="form-group">
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
                </div>
                <!-- Botón filtro -->
                <div class="button-container">
                    <button type="submit">Filtrar</button>
                </div>
            </form>

            <% if (request.getAttribute("lista_turnos") != null) { %>
            <table>
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
                                .map(turn -> {
                                    String pendingValue = turn.isPending() ? "Pendiente" : "Atendido";
                                    return "<tr><td>" + turn.getId() + "</td>"
                                            + "<td>" + turn.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy - HH:mm")) + "</td>"
                                            + "<td>" + turn.getCitizen().getNif() + "</td>"
                                            + "<td>" + turn.getProcedure().getDescription() + "</td>"
                                            + (turn.isPending() ? "<td><form action='TurnUpdateServlet' method='POST'><input type='hidden' name='turnUpdate' value='"
                                            + turn.getId()
                                            + "'><button class='button-as-link' type='submit' title='Marcar como atendido' value='" + turn.getId()
                                            + "'>" + pendingValue
                                            + "</button></form></td>" : "<td>" + pendingValue + "</td>")
                                            + "</tr>";
                                })
                                .collect(Collectors.joining());
                    %>
                    <%=table%>
                </tbody>
            </table>
            <% }%>
        </div>
    </body>
</html>