package com.epam.rd.november2017.vlasenko.controller;

import com.epam.rd.november2017.vlasenko.entity.Book;
import com.epam.rd.november2017.vlasenko.service.book.BookServiceImpl;
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
    private static final String DISPATCH_PAGE = "book.jsp";

    private BookServiceImpl bookService = new BookServiceImpl();

    private static Logger logger = LoggerFactory.getLogger(BookServlet.class.getSimpleName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter(PARAM_ID);
        if (idStr != null) {
            try {
                Integer id = Integer.valueOf(idStr);
                Book book = bookService.find(id);
                if (book != null) {
                    req.setAttribute(REQ_ATTR_BOOK, book);
                    req.getRequestDispatcher(DISPATCH_PAGE).forward(req, resp);
                } else {
                    logger.debug("There is no record in the database by this parametr.");
                    resp.sendError(404);
                }
            } catch (NumberFormatException e) {
                logger.debug("Incorrect URL parameter.", e);
                resp.sendError(404);
            } catch (SQLException e) {
                logger.error("Book detail page doesn't work!", e);
                resp.sendError(500);
            }
        }
    }
}
