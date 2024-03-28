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

    private static final Controller con = Controller.INSTANCE;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            Long turnId = Long.valueOf(request.getParameter("turnUpdate"));
            Turn turn = con.fetchTurn(turnId);
            turn.setPending(Boolean.FALSE);
            con.updateTurn(turn);

            request.setAttribute("lista_turnos", con.fetchAllTurns());
            request.getRequestDispatcher("list.jsp")
                    .forward(request, response);
        } catch (NumberFormatException | ServletException | IOException e) {
            Logger.getLogger(TurnUpdateServlet.class.getName()).severe(e.getMessage());
        }
    }

}
