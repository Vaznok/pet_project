package com.epam.rd.november2017.vlasenko.controller;

import com.epam.rd.november2017.vlasenko.exception.view.JspException;
import com.epam.rd.november2017.vlasenko.service.order.OrderServiceImpl;
import com.epam.rd.november2017.vlasenko.service.order.view.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "LibrarianServlet", urlPatterns = "/librarian")
public class LibrarianServlet extends HttpServlet {
    private static final String SESSION_ATTR_VIEWS = "views";
    private static final String LIBRARIAN_JSP = "librarian.jsp";

    private OrderServiceImpl orderService = new OrderServiceImpl();

    private static Logger logger = LoggerFactory.getLogger(LibrarianServlet.class.getSimpleName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            resp.setContentType("text/html");
            List<View> viewList = (List<View>) orderService.showNewOrders();
            HttpSession session = req.getSession();
            session.setAttribute(SESSION_ATTR_VIEWS, viewList);
            RequestDispatcher dispatcher = req.getRequestDispatcher(LIBRARIAN_JSP);
            dispatcher.forward(req, resp);
        } catch (SQLException e) {
            logger.error("Orders list doesn't work!", e.getMessage());
            resp.sendError(500);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      /*  RequestDispatcher dispatcher = req.getRequestDispatcher(ORDER_JSP);
        dispatcher.forward(req, resp);
        req.getParameter("plannedReturn");
        req.getParameter("penalty");*/
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        BufferedReader reader = new BufferedReader(new InputStreamReader(req.getInputStream()));
        String orderIdStr = reader.readLine();
        try {
            if (orderIdStr != null) {
                orderIdStr = orderIdStr.split("=")[1];
            } else {
                throw new JspException();
            }
            orderService.cancelOrder(Integer.valueOf(orderIdStr));
            PrintWriter writer = resp.getWriter();
            writer.print("refresh page");
        } catch (SQLException e) {
            logger.error("Orders list doesn't work!", e);
            resp.sendError(500);
        } catch (NumberFormatException | JspException e) {
            logger.error("Incorrect parsing /librarian 'PUT'.", e);
            resp.sendError(500);
        }
    }
}
