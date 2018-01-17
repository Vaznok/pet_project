package com.epam.rd.november2017.vlasenko.dao.jdbc.repository;

import com.epam.rd.november2017.vlasenko.dao.jdbc.datasource.DataSourceBoneCp;
import com.epam.rd.november2017.vlasenko.dao.jdbc.repository.impl.BookRepository;
import com.epam.rd.november2017.vlasenko.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BookRepositoryTest {
    private BookRepository sut = new BookRepository();
    private DataSource dataSource = new DataSourceBoneCp();

    @BeforeEach
    public void truncateTableDb() throws IOException, SQLException {
        try(Connection conn = dataSource.getConnection()) {
            Statement stat = conn.createStatement();

            stat.executeUpdate("SET FOREIGN_KEY_CHECKS = 0;");
            stat.executeUpdate("TRUNCATE TABLE books");
            stat.executeUpdate("SET FOREIGN_KEY_CHECKS = 1;");
            conn.commit();
        }
    }

    @Test
    public void createNewBook_FindCreatedBook() throws IOException, SQLException {
        Book book = new Book("Before They Are Hanged",
                            "Joe Abercrombie",
                            "Gollancz",
                            "2006-05-04");

        sut.create(book);

        assertEquals(book, sut.find(1));
    }

    @Test
    public void createNewBooks_FindCreatedBooks() throws IOException, SQLException {
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
        //create records in the database
        sut.create(booksList);

        //find created records by id
        List<Book> booksFromDb = (List<Book>) sut.find(Arrays.asList(1, 2, 3));

        assertArrayEquals(booksList.toArray(), booksFromDb.toArray());
    }

    @Test
    public void findBookByNotExistedId_NullPointerExceptionThrown() throws IOException, SQLException {
        assertThrows(NullPointerException.class, ()-> { sut.find(100); });
    }

    @Test
    public void findListOfBookWithNotExistedId_NullPointerExceptionThrown() throws IOException, SQLException {
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

        sut.create(booksList);

        assertThrows(NullPointerException.class, ()-> { sut.find(asList(1, 2, 100)); });
    }

    @Test
    public void updateBookById_FindUpdatedBook() throws IOException, SQLException {
        Book createBook = new Book("Before They Are Hanged",
                                "Don Kihot",
                                "Gollancz",
                                "2006-05-04");
        sut.create(createBook);

        Book updateBook = new Book("Before They Are Hanged",
                                "Joe Abercrombie",
                                "Gollancz",
                                "2006-05-04");
        sut.update(1, updateBook);

        assertEquals(updateBook, sut.find(1));
    }

    @Test
    public void updateBookByNotExistedId_NullPointerExceptionThrown() throws IOException, SQLException {
        Book updateBook = new Book("Before They Are Hanged",
                                "Joe Abercrombie",
                                "Gollancz",
                                "2006-05-04");

        assertThrows(NullPointerException.class, ()-> { sut.update(100, updateBook); });
    }

    @Test
    public void deleteBookById_FindDeletedBookThrowNullPointerException() throws IOException, SQLException {
        Book book = new Book("Before They Are Hanged",
                            "Joe Abercrombie",
                            "Gollancz",
                            "2006-05-04");
        sut.create(book);
        sut.delete(1);

        assertThrows(NullPointerException.class, ()-> { sut.find(1); });
    }

    @Test
    public void deleteBookByNotExistedId_NullPointerExceptionThrown() throws IOException, SQLException {
        assertThrows(NullPointerException.class, ()-> { sut.delete(100); });
    }
}

