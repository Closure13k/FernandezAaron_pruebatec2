package com.closure13k.aaronfmpt2.servlet;

import com.closure13k.aaronfmpt2.logic.Controller;
import com.closure13k.aaronfmpt2.logic.model.Citizen;
import com.closure13k.aaronfmpt2.logic.model.Procedure;
import com.closure13k.aaronfmpt2.logic.model.Turn;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "TurnServlet", urlPatterns = {"/TurnServlet"})
public class TurnServlet extends HttpServlet {

    List<Turn> turnsList = new ArrayList<>();

    Controller con = Controller.getInstance();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        applyFiltersToList(request);

        request.getRequestDispatcher("list.jsp")
                .forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Citizen citizen = con.fetchCitizen(request.getParameter("nif_turno"));
        Procedure procedure = con.fetchProcedure(Long.valueOf(request.getParameter("tramite")));
        con.createTurn(new Turn(citizen, procedure));
        response.sendRedirect("index.jsp");

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

        if (dateFilter == null) {
            turnsList = con.fetchAllTurns();
        } else {
            turnsList = con.fetchTurnsByDate(LocalDate.parse(dateFilter));
            request.setAttribute("fecha", dateFilter);

            if (statusFilter != null && !statusFilter.isBlank()) {
                final Predicate<Turn> meetsStatus = turn -> Objects.equals(
                        turn.isPending(),
                        Boolean.valueOf(statusFilter)
                );

                turnsList = turnsList.stream()
                        .filter(meetsStatus)
                        .toList();

                request.setAttribute("estado", statusFilter);
            }

        }
        request.setAttribute("lista_turnos", turnsList);
    }
}
