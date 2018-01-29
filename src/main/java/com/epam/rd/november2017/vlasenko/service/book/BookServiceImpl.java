package com.epam.rd.november2017.vlasenko.service.book;

import com.epam.rd.november2017.vlasenko.dao.jdbc.repository.BookDao;
import com.epam.rd.november2017.vlasenko.dao.jdbc.repository.impl.BookDaoImpl;
import com.epam.rd.november2017.vlasenko.dao.jdbc.transaction.TransactionHandler;
import com.epam.rd.november2017.vlasenko.entity.Book;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.epam.rd.november2017.vlasenko.config.GlobalConfig.TRANSACTION;

public class BookServiceImpl implements BookService<Book, Integer> {
    private static final Pattern bookName = Pattern.compile("^.{3,50}$");
    private static final Pattern author = Pattern.compile("^[A-Z][a-zA-Z- ]{3,30}$");
    private static final Pattern publisher = Pattern.compile("^[a-zA-Z- ]{3,20}$");

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private TransactionHandler transaction = TRANSACTION;
    private BookDao bookDao = new BookDaoImpl(transaction);

    private boolean validateBookName(String name){
        Matcher matcher = bookName.matcher(name);
        if(matcher.matches()){
            return true;
        }
        return false;
    }

    private boolean validateAuthor(String name){
        Matcher matcher = author.matcher(name);
        if(matcher.matches()){
            return true;
        }
        return false;
    }

    private boolean validatePublisher(String name){
        Matcher matcher = publisher.matcher(name);
        if(matcher.matches()){
            return true;
        }
        return false;
    }

    private boolean validateDate(String formatedDate) {
        try {
            Date date = dateFormat.parse(formatedDate);
            return new Date().getTime() >= date.getTime();
        } catch (ParseException e) {
            return false;
        }
    }

    @Override
    public String validate(Book book) throws SQLException {
        String result = null;
        String bookName = book.getName();
        String author = book.getAuthor();
        String publisher = book.getPublisher();
        String publicationDate = book.getPublicationDate();
        Integer bookCount = book.getCount();

        if (bookName.isEmpty() || author.isEmpty() || publisher.isEmpty() || publicationDate.isEmpty()) {
            result = "All fields must be filled!";
        } else if (!validateBookName(bookName)){
            result = "Book name must consist of at least 3 maximum 50 characters!";
        } else if (!validateAuthor(author)) {
            result = "Author name must consist of at least 3 maximum 30 letters! First letter must be capital";
        } else if (!validatePublisher(publisher)) {
            result = "Publisher must consist of at least 3 maximum 20 letters!";
        } else if (!validateDate(publicationDate)){
            result = "Publication date mustn't be in future!";
        } else if (bookCount < 0) {
            result = "Book count mustn't be less then '0'!";
        } else if (transaction.doInTransaction(() -> bookDao.isBookExist(book))) {
            result = "This book has already exist in database!";
        }
        return result;
    }

    @Override
    public void createBook(Book book) throws SQLException {
       transaction.doInTransaction(() -> {
            bookDao.create(book);
            return null;
        });
    }

    @Override
    public Iterable<Book> findAllExisted() throws SQLException {
        return transaction.doInTransaction(() -> bookDao.findAllExisted());
    }

    @Override
    public Iterable<Book> findAll() throws SQLException {
        return transaction.doInTransaction(() -> bookDao.findAll());
    }

    @Override
    public boolean deleteBook(Integer bookId) throws SQLException {
        Boolean success = transaction.doInTransaction(() -> bookDao.delete(bookId));
        return success;
    }

    @Override
    public Book find(Integer id) throws SQLException {
        return transaction.doInTransaction(() -> bookDao.find(id));
    }

    @Override
    public synchronized boolean changeBookCount(Integer bookId, Integer bookCount) throws SQLException {
        Boolean success = transaction.doInTransaction(() -> {
            Book book = bookDao.find(bookId);
            Integer realCountOfBooks = book.getCount();
            if (realCountOfBooks >= bookCount) {
                realCountOfBooks = realCountOfBooks - bookCount;
                book.setCount(realCountOfBooks);
                bookDao.update(bookId, book);
                return true;
            }
            return false;
        });
        return success;
    }
}
