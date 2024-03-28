<%@page import="com.closure13k.aaronfmpt2.logic.model.Procedure"%>
<%@page import="java.util.stream.Collectors"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Solicitud de Turno</title>
        <style><%@include file="res/styles.css"%></style>
    </head>
    <body>
        <div class="container-shrink">
            <h1>Solicitud de Turno</h1>
            <form action="TurnServlet" method="POST">
                <!-- Campo DNI -->
                <div class="form-group">
                    <label for="nif_turno">NIF</label>
                    <input type="text" name="nif_turno"
                           title="Formato de ejemplo: 12345678A"
                           pattern="^\d{8}[A-Z]$|^[XYZ]\d{7}[A-Z]$" required/>
                </div>
                <!-- Combo de Trámites -->
                <% if (request.getAttribute("tramites") != null) {%>
                <div class="form-group">
                    <label for="tramite">Elige un trámite</label>
                    <select name="tramite">
                        <%=createOptions((List<Procedure>) request.getAttribute("tramites"))%>
                    </select>
                </div>
                <% }%>
                <!-- Ingreso -->
                <div class="button-container">
                    <button type="submit">Confirmar Solicitud</button>
                </div>
            </form>
            <!-- Acceso a la lista de turnos -->
            <form id="turneroForm" action="TurnServlet" method="GET">
                <div class="button-container">
                    <button type="submit">Listar Turno</button>
                </div>
            </form>
    </body>
</html>
<%!
    /**
     * Crea las opciones a partir de los trámites recibidos.
     *
     * @param turnList Lista de turnos a mostrar.
     * @return Código HTML de la tabla.
     */
    private String createOptions(List<Procedure> procList) {
        return procList.stream()
                .map(proc -> "<option value=\"" + proc.getId() + "\">" + proc.getDescription() + "</option>")
                .collect(Collectors.joining());
    }

%>