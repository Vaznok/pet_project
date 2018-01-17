package com.epam.rd.november2017.vlasenko.dao.jdbc.repository;

import com.epam.rd.november2017.vlasenko.dao.jdbc.datasource.DataSourceForTest;
import com.epam.rd.november2017.vlasenko.dao.jdbc.exception.NoSuchEntityException;
import com.epam.rd.november2017.vlasenko.dao.jdbc.repository.impl.BookRepository;
import com.epam.rd.november2017.vlasenko.dao.jdbc.transaction.TransactionHandlerImpl;
import com.epam.rd.november2017.vlasenko.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BookRepositoryTest {
    private TransactionHandlerImpl transaction = new TransactionHandlerImpl(new DataSourceForTest());
    private BookRepository sut = new BookRepository(transaction);

    @BeforeEach
    public void truncateTableDb() throws SQLException, NoSuchEntityException {
        transaction.doInTransaction(() -> {
            try (Statement stat = transaction.getConnection().createStatement()) {
                stat.executeUpdate("SET FOREIGN_KEY_CHECKS = 0;");
                stat.executeUpdate("TRUNCATE TABLE books");
                return null;
            }
        });
    }

    @Test
    public void createNewBook_FindCreatedBook() throws SQLException, NoSuchEntityException {
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
    public void createNewBooks_FindCreatedBooks() throws SQLException, NoSuchEntityException {
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
    public void findBookByNotExistedId_NoSuchEntityExceptionThrown() throws SQLException {
        assertThrows(NoSuchEntityException.class, ()-> {
            transaction.doInTransaction(() -> sut.find(100));
        });
    }

    @Test
    public void findListOfBookWithNotExistedId_NoSuchEntityExceptionThrown() throws SQLException {
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
        assertThrows(NoSuchEntityException.class, ()-> transaction.doInTransaction(() -> {
                sut.create(booksList);
                return sut.find(asList(1, 2, 100));
            })
        );
    }

    @Test
    public void updateBookById_FindUpdatedBook() throws SQLException, NoSuchEntityException {
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

    @Test
    public void updateBookByNotExistedId_NoSuchEntityExceptionThrown() throws SQLException {
        Book updateBook = new Book("Before They Are Hanged",
                                "Joe Abercrombie",
                                "Gollancz",
                                "2006-05-04");

        assertThrows(NoSuchEntityException.class, ()-> transaction.doInTransaction(() -> {
                sut.update(100, updateBook);
                return null;
            })
        );
    }

    @Test
    public void deleteBookById_FindDeletedBookThrowNoSuchEntityException() throws SQLException {
        Book book = new Book("Before They Are Hanged",
                            "Joe Abercrombie",
                            "Gollancz",
                            "2006-05-04");

        assertThrows(NoSuchEntityException.class, ()-> transaction.doInTransaction(() -> {
                sut.create(book);
                sut.delete(1);
                return sut.find(1);
            })
        );
    }

    @Test
    public void deleteBookByNotExistedId_NoSuchEntityExceptionThrown() throws SQLException {
        assertThrows(NoSuchEntityException.class, ()-> transaction.doInTransaction(() -> {
                sut.delete(100);
                return null;
            })
        );
    }
}

