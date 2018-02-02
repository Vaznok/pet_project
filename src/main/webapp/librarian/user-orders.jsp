<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>

<html>
<head>
    <title>Book</title>
    <%--<style>
        <%@include file="/WEB-INF/librarian.css"%>
    </style>--%>
</head>
    <body>
    <table>
        <h2>Active orders:</h2>
        <tr>
            <th>Book name</th>
            <th>Author</th>
            <th>Publisher</th>
            <th>Publication date</th>
            <th>Count</th>
            <th>Received</th>
            <th>Deadline</th>
            <th>Penalty</th>
            <th>Returned</th>
            <th>Order status</th>
        </tr>
        <c:forEach var="order" items="${orders}">
            <tr>
                <td>${order.bookName}</td>
                <td>${order.author}</td>
                <td>${order.publisher}</td>
                <td>${order.publicationDate}</td>
                <td>${order.orderBookCount}</td>
                <td>${order.received}</td>
                <td>${order.plannedReturn}</td>
                <td>${order.penalty}</td>
                <td>${order.returned}</td>
                <td>${order.status}</td>
            </tr>
        </c:forEach>
    </table>
    </body>
</html>
