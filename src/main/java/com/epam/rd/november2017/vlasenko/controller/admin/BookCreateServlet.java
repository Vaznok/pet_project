package com.epam.rd.november2017.vlasenko.controller.admin;

import com.epam.rd.november2017.vlasenko.entity.Book;
import com.epam.rd.november2017.vlasenko.service.book.BookServiceImpl;
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

import static java.util.Objects.nonNull;

@WebServlet("/admin/book-new")
public class BookCreateServlet extends HttpServlet {
    private static final String REG_BOOK_JSP = "book-new.jsp";
    private static final String REQ_ATTR_NO_ADD = "noAdd";
    private static final String REQ_ATTR_ADD = "add";

    private BookServiceImpl bookService = new BookServiceImpl();

    private static Logger logger = LoggerFactory.getLogger(LibrarianCreateServlet.class.getSimpleName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher(REG_BOOK_JSP);
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bookName = request.getParameter("bookName");
        String author = request.getParameter("author");
        String publisher = request.getParameter("publisher");
        String publicationDate= request.getParameter("publicationDate");
        String bookCountStr = request.getParameter("count");

        try {
            Book book = new Book(bookName, author, publisher, publicationDate, Integer.valueOf(bookCountStr));
            //return value 'null' means that validation is successful otherwise return a description of the inconsistencies
            String validationResult = bookService.validate(book);

            if (nonNull(validationResult)) {
                request.setAttribute(REQ_ATTR_NO_ADD, validationResult);
                request.getRequestDispatcher(REG_BOOK_JSP).forward(request, response);
                return;
            }

            bookService.createBook(book);

            request.setAttribute(REQ_ATTR_ADD, "Book is created successfully!");
            request.getRequestDispatcher(REG_BOOK_JSP).forward(request, response);
        } catch (SQLException e) {
            logger.error("Book add error.", e);
            response.sendError(500);
        }
    }
}
