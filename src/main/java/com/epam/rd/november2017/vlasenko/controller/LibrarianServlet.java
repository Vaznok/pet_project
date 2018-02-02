package com.epam.rd.november2017.vlasenko.controller;

import javax.servlet.jsp.JspException;
import com.epam.rd.november2017.vlasenko.service.order.OrderServiceImpl;
import com.epam.rd.november2017.vlasenko.entity.view.UnitedView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "LibrarianServlet", urlPatterns = "/librarian")
public class LibrarianServlet extends HttpServlet {
    private static final String REQ_ATTR_NEW_ORDERS = "newOrders";
    private static final String REQ_ATTR_ACTIVE_ORDERS = "activeOrders";
    private static final String REQ_ATTR_EXPIRED_ORDERS = "expiredOrders";
    private static final String LIBRARIAN_JSP = "librarian.jsp";

    private OrderServiceImpl orderService = new OrderServiceImpl();

    private static Logger logger = LoggerFactory.getLogger(LibrarianServlet.class.getSimpleName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            resp.setContentType("text/html");
            List<UnitedView> newOrders = orderService.findNewOrders();
            List<UnitedView> activeOrders = orderService.findActiveOrders();
            List<UnitedView> expiredOrders = orderService.findExpiredOrders();
            req.setAttribute(REQ_ATTR_NEW_ORDERS, newOrders);
            req.setAttribute(REQ_ATTR_ACTIVE_ORDERS, activeOrders);
            req.setAttribute(REQ_ATTR_EXPIRED_ORDERS, expiredOrders);
            RequestDispatcher dispatcher = req.getRequestDispatcher(LIBRARIAN_JSP);
            dispatcher.forward(req, resp);
        } catch (SQLException e) {
            logger.error("Orders lists don't work!", e.getMessage());
            resp.sendError(500);
        }
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
            orderService.cancelNewOrder(Integer.valueOf(orderIdStr));
            PrintWriter writer = resp.getWriter();
            writer.print("refresh page");
            writer.close();
        } catch (SQLException e) {
            logger.warn("Database problem!", e);
            resp.sendError(500);
        } catch (NumberFormatException | JspException e) {
            logger.warn("Incorrect parsing!", e);
            resp.sendError(500);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(req.getInputStream()));
        String orderIdStr = reader.readLine();
        try {
            if (orderIdStr != null) {
                orderIdStr = orderIdStr.split("=")[1];
            } else {
                throw new JspException();
            }
            Integer orderId = Integer.valueOf(orderIdStr);
            orderService.returnOrder(orderId);
            PrintWriter writer = resp.getWriter();
            writer.print("refresh page");
            writer.close();
        } catch (SQLException e) {
            logger.error("Return order error.", e);
            resp.sendError(500);
        } catch (NumberFormatException | JspException e) {
            logger.error("Incorrect orderId was sent from jsp.", e);
            resp.sendError(500);
        }
    }
}
