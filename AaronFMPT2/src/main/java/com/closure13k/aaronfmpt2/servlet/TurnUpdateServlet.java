package com.closure13k.aaronfmpt2.servlet;

import com.closure13k.aaronfmpt2.logic.Controller;
import com.closure13k.aaronfmpt2.logic.model.Turn;

import java.io.IOException;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "TurnUpdateServlet", urlPatterns = {"/TurnUpdateServlet"})
public class TurnUpdateServlet extends HttpServlet {

    private static final Controller CONTROLLER = Controller.INSTANCE;

    /**
     * Recoge el turno de la base de datos y lo marca como completado.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            Long turnId = Long.valueOf(request.getParameter("turnUpdate"));
            Turn turn = CONTROLLER.fetchTurn(turnId);
            turn.setPending(Boolean.FALSE);
            CONTROLLER.updateTurn(turn);

            request.setAttribute("lista_turnos", CONTROLLER.fetchAllTurns());
            request.getRequestDispatcher("list.jsp")
                    .forward(request, response);
        } catch (NumberFormatException | ServletException | IOException e) {
            Logger.getLogger(TurnUpdateServlet.class.getName()).severe(e.getMessage());
        }
    }

}
