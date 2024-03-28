package com.closure13k.aaronfmpt2.servlet;

import com.closure13k.aaronfmpt2.logic.Controller;
import com.closure13k.aaronfmpt2.logic.NifNieInputValidator;
import com.closure13k.aaronfmpt2.logic.model.Turn;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@WebServlet(name = "TurnServlet", urlPatterns = {"/TurnServlet"})
public class TurnServlet extends HttpServlet {

    private static final Controller CONTROLLER = Controller.INSTANCE;

    /**
     * Muestra la lista de turnos y entra en/refresca el listado.
     * <br>
     * Aplica los filtros necesarios para la tabla de turnos.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        applyFiltersToList(request);
        try {
            request.getRequestDispatcher("list.jsp")
                    .forward(request, response);
        } catch (ServletException | IOException e) {
            Logger.getLogger(TurnServlet.class.getName()).severe(e.getMessage());
        }

    }

    /**
     * Crea un nuevo turno en la base de datos.
     * <br>
     * Si el NIF no es válido, redirige a la página de inicio, ya que considera
     * que el usuario ha manipulado el html, por lo que no tendría sentido
     * proporcionarle más información de la necesaria.
     * <br>
     * Si el turno se crea correctamente, redirige a la lista de turnos.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String path = "list.jsp";
        final String nif = request.getParameter("nif_turno");
        //Validación ante posible borrado (pattern="^[0-9]{8}[A-Za-z]{1}$")
        //mediante inspeccionar elemento.
        if (!NifNieInputValidator.isValidNifSimple(nif)) {
            path = "index.jsp";
        } else {
            try {
                Long procId = Long.valueOf(request.getParameter("tramite"));
                CONTROLLER.createTurn(new Turn(
                        CONTROLLER.fetchCitizen(nif),
                        CONTROLLER.fetchProcedure(procId))
                );
            } catch (NumberFormatException e) {
                Logger.getLogger(TurnServlet.class.getName()).severe(e.getMessage());
                doGet(request, response);
            }
        }
        request.setAttribute("lista_turnos", CONTROLLER.fetchAllTurns());
        try {
            request.getRequestDispatcher(path)
                    .forward(request, response);
        } catch (ServletException | IOException e) {
            Logger.getLogger(TurnServlet.class.getName()).severe(e.getMessage());
        }
    }

    /**
     * Aplica los filtros necesarios para la tabla de turnos.
     * <br>
     * Si se aplica un listado indiscriminado, devolverá todas las entradas. De
     * otro modo, filtrará por fecha. Filtrará por estado si está especificado.
     */
    private void applyFiltersToList(HttpServletRequest request) {
        String dateFilter = request.getParameter("fecha");
        String statusFilter = request.getParameter("estado");

        List<Turn> turnsList;
        if (dateFilter == null || dateFilter.isBlank()) {
            turnsList = CONTROLLER.fetchAllTurns();
        } else {
            try {
                turnsList = CONTROLLER.fetchTurnsByDate(LocalDate.parse(dateFilter));

                if (statusFilter != null && !statusFilter.isBlank()) {
                    final Predicate<Turn> meetsStatus = turn -> Objects.equals(
                            turn.isPending(),
                            Boolean.valueOf(statusFilter)
                    );

                    //Pongamos un poco de streams por aquí.
                    turnsList = turnsList.stream()
                            .filter(meetsStatus)
                            .collect(Collectors.toList());

                    request.setAttribute("estado", statusFilter);
                }
                request.setAttribute("fecha", dateFilter);
            } catch (DateTimeParseException dtpe) {
                Logger.getLogger(TurnServlet.class.getName()).severe(dtpe.getMessage());
                turnsList = CONTROLLER.fetchAllTurns();
            }
        }
        request.setAttribute("lista_turnos", turnsList);
    }
}
