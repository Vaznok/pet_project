<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8"%>

<html>
    <head>
        <title>Account</title>
        <%--<style>
            <%@include file="/WEB-INF/librarian.css"%>
        </style>--%>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script type="text/javascript">
            $(document).ready(function() {
                $('.cancel').click(function () {
                    var orderId = $('.cancel').val();
                    $.ajax({
                        url: 'http://localhost:8080/library/account',
                        type: 'DELETE',
                        data: {
                            orderId: orderId
                        },
                        success: function () {
                            location.reload();
                        },
                        error: function () {

                        }
                    });
                });
            });
        </script>
    </head>
    <body>
        <div>${sessionScope.user.nickName}</div>
        <div>${sessionScope.user.email}</div>
        <div>${sessionScope.user.firstName}</div>
        <div>${sessionScope.user.lastName}</div>
        <table>
            <h2>New orders:</h2>
            <tr>
                <th>Book name</th>
                <th>Author</th>
                <th>Publisher</th>
                <th>Publication date</th>
                <th>Count</th>
                <th></th>
            </tr>
            <c:forEach var="order" items="${newOrders}">
                <tr>
                    <td>${order.bookName}</td>
                    <td>${order.author}</td>
                    <td>${order.publisher}</td>
                    <td>${order.publicationDate}</td>
                    <td>${order.orderBookCount}</td>
                    <td>
                        <button class="cancel" type="submit" value="${order.orderId}">Cancel</button>
                    </td>
                </tr>
            </c:forEach>
        </table>

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
            </tr>
            <c:forEach var="order" items="${activeOrders}">
                <tr>
                    <td>${order.bookName}</td>
                    <td>${order.author}</td>
                    <td>${order.publisher}</td>
                    <td>${order.publicationDate}</td>
                    <td>${order.orderBookCount}</td>
                    <td>${order.received}</td>
                    <td>${order.plannedReturn}</td>
                    <c:choose>
                        <c:when test="${order.penalty != 0}">
                            <td style="color: red">${order.penalty}</td>
                        </c:when>
                        <c:otherwise>
                            <td></td>
                        </c:otherwise>
                    </c:choose>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>