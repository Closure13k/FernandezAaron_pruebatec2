<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.util.stream.Collectors"%>
<%@page import="com.closure13k.aaronfmpt2.logic.model.Turn"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            table {
                border-collapse: collapse;
                width: 100%;
            }

            th, td {
                border: 1px solid #dddddd;
                text-align: left;
                padding: 8px;
            }

            th {
                background-color: #f2f2f2;
            }

            .filters {
                margin-bottom: 20px;
            }
        </style>
    </head>
    <body>
        <div class="filters">
            <form id="filtroForm" action="/ruta-de-tu-api" method="GET">
                <label for="fecha">Filtrar por Fecha:</label>
                <input type="date" id="fecha" name="fecha" required>

                <label for="estado">Filtrar por Estado:</label>
                <select id="estado" name="estado">
                    <option value="">Cualquier Estado</option>
                    <option value="Pendiente">Pendiente</option>
                    <option value="Atendido">Atendido</option>
                </select>

                <button type="submit">Filtrar</button>
            </form>
        </div>

        <% if (request.getAttribute("lista_turnos") != null) { %>
        <table id="tablaTurnos">
            <thead>
                <tr>
                    <th>ID de Turno</th>
                    <th>Fecha</th>
                    <th>NIF del Ciudadano</th>
                    <th>Estado</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Turn> turns = (List<Turn>) request.getAttribute("lista_turnos");
                    String table = turns.stream()
                            .map(turn -> "<tr><td>" + turn.getId() + "</td>"
                            + "<td>" + turn.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy - HH:m")) + "</td>"
                            + "<td>" + turn.getCitizen().getNif() + "</td>"
                            + "<td>" + (turn.isPending() ? "Pendiente" : "Atendido")
                            + "</td></tr>")
                            .collect(Collectors.joining());
                %>
                <%=table%>
            </tbody>
        </table>
        <% }%>
    </body>
</html>
