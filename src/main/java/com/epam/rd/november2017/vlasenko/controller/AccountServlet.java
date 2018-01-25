package com.epam.rd.november2017.vlasenko.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AccountServlet", urlPatterns = "/account")
public class AccountServlet extends HttpServlet {
    private static final String ACCOUNT_JSP = "account.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher(ACCOUNT_JSP).forward(req, resp);
    }
}
