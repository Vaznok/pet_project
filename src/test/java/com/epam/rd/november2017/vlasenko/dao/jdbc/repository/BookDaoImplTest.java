package com.epam.rd.november2017.vlasenko.dao.jdbc.repository;

import com.epam.rd.november2017.vlasenko.dao.jdbc.datasource.DataSourceForTest;
import com.epam.rd.november2017.vlasenko.dao.jdbc.repository.impl.BookDaoImpl;
import com.epam.rd.november2017.vlasenko.dao.jdbc.transaction.TransactionHandlerImpl;
import com.epam.rd.november2017.vlasenko.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookDaoImplTest {
    private TransactionHandlerImpl transaction = new TransactionHandlerImpl(new DataSourceForTest());
    private BookDaoImpl sut = new BookDaoImpl(transaction);

    @BeforeEach
    public void truncateTableDb() throws SQLException {
        transaction.doInTransaction(() -> {
            try (Statement stat = transaction.getConnection().createStatement()) {
                stat.executeUpdate("SET FOREIGN_KEY_CHECKS = 0;");
                stat.executeUpdate("TRUNCATE TABLE books");
                return null;
            }
        });
    }

    @Test
    public void createNewBook_FindCreatedBook() throws SQLException {
        Book book = new Book("Before They Are Hanged",
                            "Joe Abercrombie",
                            "Gollancz",
                            "2006-05-04");

        Book foundBook = transaction.doInTransaction(() -> {
            sut.create(book);
            return sut.find(1);
        });

        assertEquals(book, foundBook);
    }

    @Test
    public void createNewBooks_FindCreatedBooks() throws SQLException {
        List<Book> booksList = new ArrayList<Book>(){
            {
                add(new Book("Before They Are Hanged",
                            "Joe Abercrombie",
                            "Gollancz",
                            "2006-05-04"));
                add(new Book("Influence: The Psychology of Persuasion",
                            "Robert B. Cialdini",
                            "Robert B. Cialdini",
                            "1990-11-18"));
                add(new Book("Everything Is Negotiable",
                            "Gavin Kennedy",
                            "Brixol",
                            "1995-06-21"));
            }
        };

        List<Book> booksFromDb = transaction.doInTransaction(() -> {
            sut.create(booksList);
            return (List<Book>) sut.find(Arrays.asList(1, 2, 3));
        });

        assertArrayEquals(booksList.toArray(), booksFromDb.toArray());
    }

    @Test
    public void findByPartName_ListOfFoundBooksReturned() throws SQLException {
        List<Book> booksList = new ArrayList<Book>(){
            {
                add(new Book("Before They Are Hanged",
                        "Joe Abercrombie",
                        "Gollancz",
                        "2006-05-04"));
                add(new Book("Before They Are Hanged 2",
                        "Joe Abercrombie",
                        "Gollancz",
                        "2009-11-02"));
                add(new Book("Everything Is Negotiable",
                        "Gavin Kennedy",
                        "Brixol",
                        "1995-06-21"));
            }
        };
        List<Book> foundBooks = transaction.doInTransaction(() -> {
            sut.create(booksList);
            return (List<Book>) sut.findByName("Hanged");
        });
        booksList.remove(2);
        assertArrayEquals(booksList.toArray(), foundBooks.toArray());
    }

    @Test
    public void findByPartAuthor_ListOfFoundBooksReturned() throws SQLException {
        List<Book> booksList = new ArrayList<Book>(){
            {
                add(new Book("Before They Are Hanged",
                        "Joe Abercrombie",
                        "Gollancz",
                        "2006-05-04"));
                add(new Book("Before They Are Hanged 2",
                        "Joe Abercrombie",
                        "Gollancz",
                        "2009-11-02"));
                add(new Book("Everything Is Negotiable",
                        "Gavin Kennedy",
                        "Brixol",
                        "1995-06-21"));
            }
        };
        List<Book> foundBooks = transaction.doInTransaction(() -> {
            sut.create(booksList);
            return (List<Book>) sut.findByAuthor("Joe");
        });
        booksList.remove(2);
        assertArrayEquals(booksList.toArray(), foundBooks.toArray());
    }

    @Test
    public void findAll_ListOfFoundBooksReturned() throws SQLException {
        List<Book> booksList = new ArrayList<Book>(){
            {
                add(new Book("Before They Are Hanged",
                        "Joe Abercrombie",
                        "Gollancz",
                        "2006-05-04"));
                add(new Book("Before They Are Hanged 2",
                        "Joe Abercrombie",
                        "Gollancz",
                        "2009-11-02"));
                add(new Book("Everything Is Negotiable",
                        "Gavin Kennedy",
                        "Brixol",
                        "1995-06-21"));
            }
        };
        List<Book> foundBooks = transaction.doInTransaction(() -> {
            sut.create(booksList);
            return (List<Book>) sut.findAll();
        });
        assertArrayEquals(booksList.toArray(), foundBooks.toArray());
    }

    @Test
    public void updateBookById_FindUpdatedBook() throws SQLException {
        Book createBook = new Book("Before They Are Hanged",
                                "Don Kihot",
                                "Gollancz",
                                "2006-05-04");
        Book updateBook = new Book("Before They Are Hanged",
                                "Joe Abercrombie",
                                "Gollancz",
                                "2006-05-04");
        Book foundBook = transaction.doInTransaction(() -> {
            sut.create(createBook);
            sut.update(1, updateBook);
            return sut.find(1);
        });

        assertEquals(updateBook, foundBook);
    }
}

