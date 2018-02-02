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
import javax.servlet.jsp.JspException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import static com.epam.rd.november2017.vlasenko.config.GlobalConfig.SESSION_USER_ATTRIBUTE_NAME;

@WebServlet(name = "AccountServlet", urlPatterns = "/account")
public class AccountServlet extends HttpServlet {
    private static final String ACCOUNT_JSP = "account.jsp";
    private static final String REQ_ATTR_NEW_ORDERS = "newOrders";
    private static final String REQ_ATTR_ACTIVE_ORDERS = "activeOrders";

    private OrderServiceImpl orderService = new OrderServiceImpl();

    private static Logger logger = LoggerFactory.getLogger(AccountServlet.class.getSimpleName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute(SESSION_USER_ATTRIBUTE_NAME);
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
            orderService.deleteOrder(Integer.valueOf(orderIdStr));
            PrintWriter writer = resp.getWriter();
            writer.print("refresh page");
        } catch (SQLException e) {
            logger.error("New order delete by client fault!", e);
            resp.sendError(500);
        } catch (NumberFormatException | JspException e) {
            logger.error("Incorrect parsing orderId!", e);
            resp.sendError(500);
        }
    }
}
