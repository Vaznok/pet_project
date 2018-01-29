package com.epam.rd.november2017.vlasenko.controller;

import com.epam.rd.november2017.vlasenko.controller.admin.UserBlockServlet;
import com.epam.rd.november2017.vlasenko.entity.User;
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

@WebServlet(name = "AccountServlet", urlPatterns = "/account")
public class AccountServlet extends HttpServlet {
    private static final String ACCOUNT_JSP = "account.jsp";
    private static final String REQ_ATTR_NEW_ORDERS = "newOrders";
    private static final String REQ_ATTR_ACTIVE_ORDERS = "activeOrders";

    private OrderServiceImpl orderService = new OrderServiceImpl();

    private static Logger logger = LoggerFactory.getLogger(UserBlockServlet.class.getSimpleName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        Integer userId = user.getId();

        try {
            List<UnitedView> newOrders = orderService.findClientNewOrders(userId);
            List<UnitedView> activeOrders = orderService.findClientActiveOrders(Integer.valueOf(userId));
            req.setAttribute(REQ_ATTR_NEW_ORDERS, newOrders);
            req.setAttribute(REQ_ATTR_ACTIVE_ORDERS, activeOrders);
            req.getRequestDispatcher(ACCOUNT_JSP).forward(req, resp);
        } catch (NumberFormatException e) {
            logger.warn("Incorrect parsing of userId!", e);
            resp.setStatus(500);
        } catch (SQLException e) {
            logger.warn("Fault to load personal account page!", e);
            resp.setStatus(500);
        }
    }
}
