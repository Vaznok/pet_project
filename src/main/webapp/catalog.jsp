<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
    <head>
        <title>Book Catalog</title>
        <h3>Book Catalog.</h3>
    </head>
    <body>
        <b>ALL BOOKS</b>
        <ul>
            <c:forEach var="book" items="${books}">
                <li>
                    <p>${book.name} ${book.author} ${book.publisher} ${book.publicationDate} ${book.count}</p>
                </li>
            </c:forEach>
        </ul>
    </body>
</html>
