package com.closure13k.aaronfmpt2.servlet;

import com.closure13k.aaronfmpt2.logic.Controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "ProcedureServlet", urlPatterns = {"/ProcedureServlet"})
public class ProcedureServlet extends HttpServlet {

    private static final Controller CONTROLLER = Controller.INSTANCE;

    /**
     * Devuelve los trámites de la base de datos para cargarlos en el combobox
     * del formulario.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setAttribute("tramites", CONTROLLER.fetchAllProcedures());
            request.getRequestDispatcher("register.jsp")
                    .forward(request, response);
        } catch (ServletException | IOException e) {
            Logger.getLogger(ProcedureServlet.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
