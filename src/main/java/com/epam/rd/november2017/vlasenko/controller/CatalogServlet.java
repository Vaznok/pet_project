package com.epam.rd.november2017.vlasenko.controller;

import com.epam.rd.november2017.vlasenko.dao.jdbc.datasource.DataSourceBoneCp;
import com.epam.rd.november2017.vlasenko.dao.jdbc.datasource.DataSourceForTest;
import com.epam.rd.november2017.vlasenko.dao.jdbc.exception.NoSuchEntityException;
import com.epam.rd.november2017.vlasenko.dao.jdbc.repository.BookDao;
import com.epam.rd.november2017.vlasenko.dao.jdbc.repository.impl.BookDaoImpl;
import com.epam.rd.november2017.vlasenko.dao.jdbc.transaction.TransactionBody;
import com.epam.rd.november2017.vlasenko.dao.jdbc.transaction.TransactionHandlerImpl;
import com.epam.rd.november2017.vlasenko.entity.Book;
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
import java.util.List;

@WebServlet("/catalog")
public class CatalogServlet extends HttpServlet {
    private static final String ATTRIBUTE_MODEL_TO_VIEW = "books";
    private static final String PAGE_OK = "catalog.jsp";
    private static final String PAGE_ERROR = "error.jsp";

    private TransactionHandlerImpl transaction = new TransactionHandlerImpl(new DataSourceForTest());
    private BookDaoImpl books = new BookDaoImpl(new DataSourceForTest());

    private static Logger logger = LoggerFactory.getLogger(DataSourceBoneCp.class.getSimpleName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            resp.setContentType("text/html");
            List<Book> bookList = transaction.doInTransaction(() -> (List<Book>) books.findAll());
            req.setAttribute(ATTRIBUTE_MODEL_TO_VIEW, bookList);
            RequestDispatcher dispatcher = req.getRequestDispatcher(PAGE_OK);
            dispatcher.forward(req, resp);
            return;
        } catch (SQLException | NoSuchEntityException e) {
            logger.error("Book catalog doesn't work!", e.getMessage());
        }
        resp.sendRedirect(PAGE_ERROR);
    }
}
