package com.closure13k.aaronfmpt2.servlet;

import com.closure13k.aaronfmpt2.logic.Controller;
import com.closure13k.aaronfmpt2.logic.InputValidator;
import com.closure13k.aaronfmpt2.logic.model.Turn;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@WebServlet(name = "TurnServlet", urlPatterns = {"/TurnServlet"})
public class TurnServlet extends HttpServlet {

    private static final Controller con = Controller.INSTANCE;

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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String path = "list.jsp";
        final String nif = request.getParameter("nif_turno");
        //Validación ante posible borrado (pattern="^[0-9]{8}[A-Za-z]{1}$")
        //mediante inspeccionar elemento.
        if (!InputValidator.isValidNif(nif)) {
            path = "index.jsp";
        } else {
            try {
                Long procId = Long.valueOf(request.getParameter("tramite"));
                con.createTurn(new Turn(
                        con.fetchCitizen(nif),
                        con.fetchProcedure(procId))
                );
            } catch (NumberFormatException e) {
                Logger.getLogger(TurnServlet.class.getName()).severe(e.getMessage());
                doGet(request, response);
            }
        }
        request.setAttribute("lista_turnos", con.fetchAllTurns());
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
        if (dateFilter == null) {
            turnsList = con.fetchAllTurns();
        } else {
            request.setAttribute("fecha", dateFilter);
            turnsList = con.fetchTurnsByDate(LocalDate.parse(dateFilter));

            if (statusFilter != null && !statusFilter.isBlank()) {
                request.setAttribute("estado", statusFilter);

                final Predicate<Turn> meetsStatus = turn -> Objects.equals(
                        turn.isPending(),
                        Boolean.valueOf(statusFilter)
                );

                //Pongamos un poco de streams por aquí.
                turnsList = turnsList.stream()
                        .filter(meetsStatus)
                        .collect(Collectors.toList());
            }
        }
        request.setAttribute("lista_turnos", turnsList);
    }
}
