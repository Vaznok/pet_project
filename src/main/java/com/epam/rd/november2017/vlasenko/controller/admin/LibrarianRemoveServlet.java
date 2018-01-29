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

@WebServlet(urlPatterns = "/admin/librarian-del")
public class LibrarianRemoveServlet extends HttpServlet {
    private static final String DEL_LIBRARIAN_JSP = "librarian-delete.jsp";
    private static final String REQ_ATTR_LIBRARIANS = "librarians";

    private UserServiceImpl userService = new UserServiceImpl();

    private static Logger logger = LoggerFactory.getLogger(LibrarianRemoveServlet.class.getSimpleName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<User> userList = userService.findLibrarians();
            req.setAttribute(REQ_ATTR_LIBRARIANS, userList);
            RequestDispatcher dispatcher = req.getRequestDispatcher(DEL_LIBRARIAN_JSP);
            dispatcher.forward(req, resp);
        } catch (SQLException e) {
            logger.error("Select librarians fault!", e);
            resp.setStatus(500);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        BufferedReader reader = new BufferedReader(new InputStreamReader(req.getInputStream()));
        String librarianIdStr = reader.readLine();
        try {
            if (librarianIdStr != null) {
                librarianIdStr = librarianIdStr.split("=")[1];
            } else {
                throw new JspException();
            }
            userService.deleteLibrarian(Integer.valueOf(librarianIdStr));
            PrintWriter writer = resp.getWriter();
            writer.print("refresh page");
        } catch (SQLException e) {
            logger.error("Librarians list doesn't work!", e);
            resp.sendError(500);
        } catch (NumberFormatException | JspException e) {
            logger.error("Incorrect parsing!", e);
            resp.sendError(500);
        }
    }
}
