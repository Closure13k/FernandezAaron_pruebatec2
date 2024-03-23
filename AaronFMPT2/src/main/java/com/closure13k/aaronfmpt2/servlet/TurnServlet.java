package com.closure13k.aaronfmpt2.servlet;

import com.closure13k.aaronfmpt2.logic.Controller;
import com.closure13k.aaronfmpt2.logic.model.Citizen;
import com.closure13k.aaronfmpt2.logic.model.Procedure;
import com.closure13k.aaronfmpt2.logic.model.Turn;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "TurnServlet", urlPatterns = {"/TurnServlet"})
public class TurnServlet extends HttpServlet {

    Controller con = Controller.getInstance();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setAttribute("lista_turnos", con.fetchAllTurns());
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
}
