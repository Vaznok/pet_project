package com.epam.rd.november2017.vlasenko.controller;

import com.epam.rd.november2017.vlasenko.dao.jdbc.datasource.DataSourceBoneCp;
import com.epam.rd.november2017.vlasenko.dao.jdbc.datasource.DataSourceForTest;
import com.epam.rd.november2017.vlasenko.dao.jdbc.exception.NoSuchEntityException;
import com.epam.rd.november2017.vlasenko.dao.jdbc.repository.impl.UserDaoImpl;
import com.epam.rd.november2017.vlasenko.dao.jdbc.transaction.TransactionHandlerImpl;
import com.epam.rd.november2017.vlasenko.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final String ATTRIBUTE_SUCCESSFUL_LOGIN = "user";
    private static final String ATTRIBUTE_UNSUCCESSFUL_LOGIN = "noSuchUser";
    private static final String PAGE_OK = "welcome.jsp";
    private static final String PAGE_ERROR = "error.jsp";

    private TransactionHandlerImpl transaction = new TransactionHandlerImpl(new DataSourceForTest());
    private UserDaoImpl userDao = new UserDaoImpl(new DataSourceForTest());

    private static Logger logger = LoggerFactory.getLogger(DataSourceBoneCp.class.getSimpleName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username.length() > 0 && password.length() > 0) {
            User user = null;
            try {
                user = transaction.doInTransaction(() -> userDao.findAuthorizedUser(username.toCharArray(), password.toCharArray()));
            } catch (SQLException e) {
                response.sendRedirect(PAGE_ERROR);
            } catch (NoSuchEntityException e) {
                e.printStackTrace();
            }
            if (user != null) {
                request.getSession().setAttribute(ATTRIBUTE_SUCCESSFUL_LOGIN, user);
                response.sendRedirect(PAGE_OK);
                return;
            } else {
                request.setAttribute(ATTRIBUTE_UNSUCCESSFUL_LOGIN, "false");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        }

    }
}
