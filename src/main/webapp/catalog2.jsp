<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>

<html>
<head>
    <title>Book catalog.</title>
    <h3>Book catalog</h3>
    <link rel="stylesheet" href="/bootstrap.min.css" media="screen"/>
    <style>
        <jsp:include page="WEB-INF/catalog.css"/>
    </style>
</head>
    <body>
        <table>
            <tr>
                <th>Book name</th>
                <th>Author</th>
                <th>Publisher</th>
                <th>Publication date</th>
                <th>Availability</th>
            </tr>
            <c:forEach var="book" items="${books}">
                <tr>
                    <td>${book.name}</td>
                    <td>${book.author}</td>
                    <td>${book.publisher}</td>
                    <td>${book.publicationDate}</td>
                    <td>${book.count}</td>
                    <button></button>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>