package com.epam.rd.november2017.vlasenko.controller.admin;

import com.epam.rd.november2017.vlasenko.entity.User;
import com.epam.rd.november2017.vlasenko.service.user.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
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

@WebServlet(name="userBlockServlet", urlPatterns = "/admin/user-block")
public class UserBlockServlet extends HttpServlet {
    private static final String BLOCK_USER_JSP = "user-block.jsp";
    private static final String REQ_ATTR_BLOCK_USERS = "blockUsers";
    private static final String REQ_ATTR_NO_BLOCK_USERS = "noBlockUsers";

    private UserServiceImpl userService = new UserServiceImpl();

    private static Logger logger = LoggerFactory.getLogger(UserBlockServlet.class.getSimpleName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<User> userBlockList = userService.findBlocked();
            List<User> userNoBlockList = userService.findNoBlocked();
            req.setAttribute(REQ_ATTR_BLOCK_USERS, userBlockList);
            req.setAttribute(REQ_ATTR_NO_BLOCK_USERS, userNoBlockList);
            RequestDispatcher dispatcher = req.getRequestDispatcher(BLOCK_USER_JSP);
            dispatcher.forward(req, resp);
        } catch (SQLException e) {
            logger.error("Select users error!", e);
            resp.setStatus(500);
        }
    }

    //use to block user
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("blockUserId");

        try {
            userService.blockUser(Integer.valueOf(userId));
        } catch (SQLException e) {
            logger.warn("Block user error!", e);
            resp.setStatus(500);
        } catch (NumberFormatException e) {
            logger.warn("Parsing fault - incorrect userId was gotten.", e);
            resp.setStatus(500);
        }
        resp.setStatus(302);
        resp.setHeader("Location", "http://localhost:8080/library/admin/user-block");
    }

    //use to unblock user
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        BufferedReader reader = new BufferedReader(new InputStreamReader(req.getInputStream()));
        String userIdStr = reader.readLine();
        try {
            if (userIdStr != null) {
                userIdStr = userIdStr.split("=")[1];
            } else {
                throw new JspException();
            }
            userService.unBlockUser(Integer.valueOf(userIdStr));
            PrintWriter writer = resp.getWriter();
            writer.print("refresh page");
            writer.close();
        } catch (SQLException e) {
            logger.warn("Unblock user error!", e);
            resp.sendError(500);
        } catch (NumberFormatException | JspException e) {
            logger.warn("Incorrect parsing!", e);
            resp.sendError(500);
        }
    }
}
