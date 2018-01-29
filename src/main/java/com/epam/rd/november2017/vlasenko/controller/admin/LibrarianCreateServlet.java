package com.epam.rd.november2017.vlasenko.controller.admin;

import com.epam.rd.november2017.vlasenko.entity.User;
import com.epam.rd.november2017.vlasenko.service.registration.RegistrationServiceImpl;
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

import static com.epam.rd.november2017.vlasenko.entity.User.Role.LIBRARIAN;
import static java.util.Objects.nonNull;

@WebServlet(urlPatterns = "/admin/librarian-reg")
public class LibrarianCreateServlet extends HttpServlet {
    private static final String REG_LIBRARIAN_JSP = "librarian-registration.jsp";
    private static final String REQ_ATTR_NO_REG = "noRegister";
    private static final String REQ_ATTR_REG = "register";

    private RegistrationServiceImpl registration = new RegistrationServiceImpl();

    private static Logger logger = LoggerFactory.getLogger(LibrarianCreateServlet.class.getSimpleName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher(REG_LIBRARIAN_JSP);
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String nickName = request.getParameter("nickName");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String contact = request.getParameter("contact");

        User user = new User(email, nickName, password, LIBRARIAN, false, firstName, lastName, contact);

        try {
            //return value 'null' means that validation is successful otherwise return a description of the inconsistencies
            String validationResult = registration.validate(user);

            if (nonNull(validationResult)) {
                request.setAttribute(REQ_ATTR_NO_REG, validationResult);
                request.getRequestDispatcher(REG_LIBRARIAN_JSP).forward(request, response);
                return;
            }

            registration.createUser(user);

            request.setAttribute(REQ_ATTR_REG, "Librarian is registered successfully!");
            request.getRequestDispatcher(REG_LIBRARIAN_JSP).forward(request, response);
        } catch (SQLException e) {
            logger.error("Registration error.", e);
            response.sendError(500);
        }
    }
}
