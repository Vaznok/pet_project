package com.epam.rd.november2017.vlasenko.controller;

import com.epam.rd.november2017.vlasenko.dao.jdbc.datasource.DataSourceForTest;
import com.epam.rd.november2017.vlasenko.dao.jdbc.exception.NoSuchEntityException;
import com.epam.rd.november2017.vlasenko.dao.jdbc.repository.impl.BookDaoImpl;
import com.epam.rd.november2017.vlasenko.dao.jdbc.transaction.TransactionHandlerImpl;
import com.epam.rd.november2017.vlasenko.entity.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/book")
public class BookServlet extends HttpServlet {
    private static final String PARAM_ID = "id";
    private static final String REQ_ATTR_BOOK = "book";
    private static final String PAGE_OK = "book.jsp";
    private static final String PAGE_ERROR = "error.jsp";

    private TransactionHandlerImpl transaction = new TransactionHandlerImpl(new DataSourceForTest());
    private BookDaoImpl bookDao = new BookDaoImpl(new DataSourceForTest());

    private static Logger logger = LoggerFactory.getLogger(BookServlet.class.getSimpleName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter(PARAM_ID);
        if (idStr != null) {
            try {
                Integer id = Integer.valueOf(idStr);
                Book book = transaction.doInTransaction(() -> bookDao.find(id));
                req.setAttribute(REQ_ATTR_BOOK, book);
                req.getRequestDispatcher(PAGE_OK).forward(req, resp);
            } catch (NumberFormatException | NoSuchEntityException e) {
                logger.debug("Incorrect URL parameter.", e.getMessage());
                resp.sendError(404);
            } catch (SQLException e) {
                logger.error("Book detail page doesn't work!", e.getMessage());
                resp.setStatus(500);
                resp.sendRedirect(PAGE_ERROR);
            }
        }
    }
}
