package com.epam.rd.november2017.vlasenko.controller;

import com.epam.rd.november2017.vlasenko.entity.view.UnitedView;
import com.epam.rd.november2017.vlasenko.service.order.OrderServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/librarian/user")
public class UserOrdersServlet extends HttpServlet {
    private static final String USER_ORDERS_JSP = "user-orders.jsp";
    private static final String REQ_ATTR_USER_ORDERS = "orders";

    private OrderServiceImpl orderService = new OrderServiceImpl();

    private static Logger logger = LoggerFactory.getLogger(UserOrdersServlet.class.getSimpleName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userIdStr = req.getParameter("id");
        try {
            Integer userId = Integer.valueOf(userIdStr);
            List<UnitedView> listOrders = orderService.findAllClientOrders(userId);
            req.setAttribute(REQ_ATTR_USER_ORDERS, listOrders);
            req.getRequestDispatcher(USER_ORDERS_JSP).forward(req, resp);
        } catch (NumberFormatException e) {
            logger.debug("Incorrect URL parameter.", e);
            resp.sendError(404);
        } catch (SQLException e) {
            logger.error("User orders detail page doesn't work!", e);
            resp.sendError(500);
        }
    }
}
