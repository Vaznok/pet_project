package com.epam.rd.november2017.vlasenko.controller;

import com.epam.rd.november2017.vlasenko.dao.jdbc.exception.NoSuchEntityException;
import com.epam.rd.november2017.vlasenko.entity.User;
import com.epam.rd.november2017.vlasenko.service.encryption.EncryptionService;
import com.epam.rd.november2017.vlasenko.service.registration.RegistrationService;
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

import static com.epam.rd.november2017.vlasenko.entity.User.Role.REGISTERED_USER;
import static java.util.Objects.nonNull;

@WebServlet("/check-in")
public class CheckInServlet extends HttpServlet {
    private final static String CHECK_IN_JSP = "check-in.jsp";
    private static final String SESSION_ATTRIBUTE_USER = "user";
    private static final String REQ_ATTR_NO_REG = "noRegister";
    private static final String PAGE_NO_REG = "check-in.jsp";
    private static final String PAGE_ERROR = "error.jsp";

    private RegistrationService registration = new RegistrationService();
    private EncryptionService encryption = new EncryptionService();

    private static Logger logger = LoggerFactory.getLogger(LoginServlet.class.getSimpleName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(CHECK_IN_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String nickName = request.getParameter("nickName");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String contact = request.getParameter("contact");

        User user = new User(email, password, nickName, REGISTERED_USER, false, firstName, lastName, contact);

        //return value 'null' means that validation is successful otherwise return a description of the inconsistencies
        String validationResult = registration.validate(user);
        if (nonNull(validationResult)) {
            request.setAttribute(REQ_ATTR_NO_REG, validationResult);
            request.getRequestDispatcher(PAGE_NO_REG).forward(request, response);
            return;
        }
        try {
            registration.createUser(user);
        } catch (SQLException e) {
            logger.error(e.getMessage());
            response.setStatus(500);
            response.sendRedirect(PAGE_ERROR);
            return;
        } catch (NoSuchEntityException e) {
            logger.debug(e.getMessage());
            request.setAttribute(REQ_ATTR_NO_REG, "No account for this email and password!");
            request.getRequestDispatcher(PAGE_NO_REG).forward(request, response);
            return;
        }

        response.addCookie(new Cookie("email", encryption.encrypt(email)));
        response.addCookie(new Cookie("password", encryption.encrypt(password)));
        request.getSession().setAttribute(SESSION_ATTRIBUTE_USER, user);
        response.sendRedirect(request.getContextPath());

    }
}
