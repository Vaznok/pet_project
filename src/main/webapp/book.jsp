<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>

<html>
    <head>
        <title>Book</title>
        <h3>${book.name}</h3>
        <h4>${book.author}</h4>
        <h4>${book.publisher}</h4>
        <h4>${book.publicationDate}</h4>
    </head>
    <body>
        <c:choose>
            <c:when test="${sessionScope.user.role == 'ADMINISTRATOR'}">
                <input type="submit" value="Order">
                <input type="submit" value="Update">
                <input type="submit" value="Delete">
            </c:when>
            <c:when test="${sessionScope.user.role != null}">
                <input type="submit" value="Order">
            </c:when>
            <c:otherwise>
            </c:otherwise>
        </c:choose>
    <form name="order" method="post" action="http://localhost:8080/library/order">

    </form>
    </body>
</html>
