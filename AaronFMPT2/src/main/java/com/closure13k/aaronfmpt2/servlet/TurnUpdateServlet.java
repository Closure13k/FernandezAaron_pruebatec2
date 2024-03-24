package com.closure13k.aaronfmpt2.servlet;

import com.closure13k.aaronfmpt2.logic.Controller;
import com.closure13k.aaronfmpt2.logic.model.Turn;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "TurnUpdateServlet", urlPatterns = {"/TurnUpdateServlet"})
public class TurnUpdateServlet extends HttpServlet {

    Controller con = Controller.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String turnId = request.getParameter("turnUpdate");
        Turn turn = con.fetchTurn(Long.valueOf(turnId));
        turn.setPending(Boolean.FALSE);
        con.updateTurn(turn);
        
        response.sendRedirect("index.jsp");
        
        

    }

}
