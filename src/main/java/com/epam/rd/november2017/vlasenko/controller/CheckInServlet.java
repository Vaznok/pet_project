package com.epam.rd.november2017.vlasenko.controller;

import com.epam.rd.november2017.vlasenko.entity.User;
import com.epam.rd.november2017.vlasenko.service.encryption.EncryptionServiceImpl;
import com.epam.rd.november2017.vlasenko.service.registration.RegistrationServiceImpl;
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

    private RegistrationServiceImpl registration = new RegistrationServiceImpl();
    private EncryptionServiceImpl encryption = new EncryptionServiceImpl();

    private static Logger logger = LoggerFactory.getLogger(CheckInServlet.class.getSimpleName());

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

        User user = new User(email, nickName, password, REGISTERED_USER, false, firstName, lastName, contact);

        try {
            //return value 'null' means that validateOrderConfirmation is successful otherwise return a description of the inconsistencies
            String validationResult = registration.validate(user);

            if (nonNull(validationResult)) {
                request.setAttribute(REQ_ATTR_NO_REG, validationResult);
                request.getRequestDispatcher(CHECK_IN_JSP).forward(request, response);
                return;
            }

            registration.createUser(user);

        } catch (SQLException e) {
            logger.error("Registration error.", e);
            response.sendError(500);
            return;
        }

        response.addCookie(new Cookie("email", encryption.encrypt(email)));
        response.addCookie(new Cookie("password", encryption.encrypt(password)));
        request.getSession().setAttribute(SESSION_ATTRIBUTE_USER, user);
        response.sendRedirect(request.getContextPath());
    }
}
