package com.epam.rd.november2017.vlasenko.controller;

import com.epam.rd.november2017.vlasenko.entity.User;
import com.epam.rd.november2017.vlasenko.service.authentication.AuthenticationServiceImpl;
import com.epam.rd.november2017.vlasenko.service.encryption.EncryptionServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.epam.rd.november2017.vlasenko.config.GlobalConfig.SESSION_USER_ATTRIBUTE_NAME;
import static java.util.Objects.nonNull;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final String REQ_ATTR_NO_LOGIN = "noLogin";
    private static final String PAGE_NO_LOGIN = "login.jsp";

    private AuthenticationServiceImpl authentication = new AuthenticationServiceImpl();
    private EncryptionServiceImpl encryption = new EncryptionServiceImpl();

    private static Logger logger = LoggerFactory.getLogger(LoginServlet.class.getSimpleName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        //return value 'null' means that validateOrderConfirmation is successful otherwise return a description of the inconsistencies
        String validationResult = authentication.validateSyntax(email, password);
        if (nonNull(validationResult)) {
            request.setAttribute(REQ_ATTR_NO_LOGIN, validationResult);
            request.getRequestDispatcher(PAGE_NO_LOGIN).forward(request, response);
            return;
        }
        User user;
        try {
            user = authentication.getUser(email, password);
        } catch (SQLException e) {
            logger.error("Login error.", e);
            response.sendError(500);
            return;
        }
        if (user != null) {
            response.addCookie(new Cookie("email", encryption.encrypt(email)));
            response.addCookie(new Cookie("password", encryption.encrypt(password)));
            request.getSession().setAttribute(SESSION_USER_ATTRIBUTE_NAME, user);
            response.sendRedirect(request.getContextPath());
        } else {
            request.setAttribute(REQ_ATTR_NO_LOGIN, "No account for this email and password!");
            request.getRequestDispatcher(PAGE_NO_LOGIN).forward(request, response);
        }
    }
}
