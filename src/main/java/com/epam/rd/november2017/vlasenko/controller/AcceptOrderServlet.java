package com.epam.rd.november2017.vlasenko.controller;

import com.epam.rd.november2017.vlasenko.service.order.OrderServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static java.util.Objects.nonNull;

@WebServlet(name = "Accept-order", urlPatterns = "/librarian/order")
public class AcceptOrderServlet extends HttpServlet {
    private static final String REQ_ATTR_ORDER_ID = "orderId";
    private static final String ORDER_JSP = "order-accept.jsp";
    private static final String REQ_ATTR_NO_VALIDATION = "noValidation";

    private OrderServiceImpl orderService = new OrderServiceImpl();

    private static Logger logger = LoggerFactory.getLogger(LibrarianServlet.class.getSimpleName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(REQ_ATTR_ORDER_ID, req.getParameter("id"));
        RequestDispatcher dispatcher = req.getRequestDispatcher(ORDER_JSP);
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String plannedReturn = req.getParameter("plannedReturn");
        String penaltyStr = req.getParameter("penalty");
        String id = req.getParameter("id");

        //return value 'null' means that validation is successful otherwise return a description of the inconsistencies
        String validationResult = orderService.validateOrderConfirmation(plannedReturn, penaltyStr);
        if (nonNull(validationResult)) {
            req.setAttribute(REQ_ATTR_NO_VALIDATION, validationResult);
            req.getRequestDispatcher(ORDER_JSP).forward(req, resp);
            return;
        }
        try {
            Integer orderId = Integer.valueOf(id);
            Integer penalty = Integer.valueOf(penaltyStr);
            orderService.acceptOrder(orderId, plannedReturn, penalty);
            resp.setStatus(HttpServletResponse.SC_FOUND);
            resp.setHeader("Location", "http://localhost:8080/library/librarian");
        } catch (SQLException e) {
            logger.error("Order confirmation error.", e);
            resp.sendError(500);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
