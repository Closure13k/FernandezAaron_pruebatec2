<%@page import="java.util.function.Function"%>
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
                <!-- Solicitar filtro -->
                <div class="button-container">
                    <button type="submit">Filtrar</button>
                </div>
            </form>

            <!-- Tabla de turnos -->
            <% if (request.getAttribute("lista_turnos") != null) {%>
            <table>
                <caption>Listado de Turnos</caption>
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
                    <%=createTable((List<Turn>) request.getAttribute("lista_turnos"))%>
                </tbody>
            </table>
            <% }%>
        </div>
    </body>
</html>
<%!
    /**
     * Crea una tabla HTML con los turnos recibidos.
     *
     * @param turns Lista de turnos a mostrar.
     * @return Código HTML de la tabla.
     */
    private String createTable(List<Turn> turns) {
        return turns.stream()
                .map(asTableRow)
                .collect(Collectors.joining());
    }

    /**
     * Convierte el turno en fila.
     * <br>
     * Si el turno está como pendiente, genera un botón con POST para
     * actualizarlo.
     */
    Function<Turn, String> asTableRow = turn -> {
        String formatDate = turn.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy - HH:mm"));

        StringBuilder builder = new StringBuilder("<tr><td>")
                .append(turn.getId()).append("</td><td>")
                .append(formatDate).append("</td><td>")
                .append(turn.getCitizen().getNif()).append("</td><td>")
                .append(turn.getProcedure().getDescription()).append("</td>");

        String formPart;
        if (turn.isPending()) {
            formPart = "<td><form action='TurnUpdateServlet' method='POST'><input type='hidden' name='turnUpdate' value='"
                    + turn.getId() + "'><button class='button-as-link' type='submit' title='Marcar como atendido' value='"
                    + turn.getId() + "'>Pendiente</button></form></td>";
        } else {
            formPart = "<td>Atendido</td>";
        }

        return builder.append(formPart).append("</tr>").toString();
    };


%>