<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8"%>

<html>
    <head>
        <title>Orders</title>
        <style>
            <%@include file="/WEB-INF/librarian.css"%>
        </style>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script type="text/javascript">
            $(document).ready(function() {
                $('.cancel').click(  function () {
                    var sendOrderId = $('.cancel').val();
                    $.ajax({
                        url: 'http://localhost:8080/library/librarian',
                        type: 'DELETE',
                        data: {
                            orderId: sendOrderId
                        },
                        success: function () {
                            location.reload();
                        },
                        error: function () {
                            /*window.location.replace("http://localhost:8080/library/error.jsp");*/
                        }
                    });
                });
            });
        </script>
        <script>
            $(document).ready(function() {
                $('.return').click(  function () {
                    var sendOrderId = $('.return').val();
                    $.ajax({
                        url: 'http://localhost:8080/library/librarian',
                        type: 'PUT',
                        data: {
                            orderId: sendOrderId
                        },
                        success: function () {
                            location.reload();
                        },
                        error: function () {
                            /*window.location.replace("http://localhost:8080/library/error.jsp");*/
                        }
                    });
                });
            });
        </script>
    </head>
    <body>
        <h3>New orders:</h3>
        <table>
            <tr>
                <th>User nickname</th>
                <th>Author</th>
                <th>Publisher</th>
                <th>Publication date</th>
                <th>Count</th>
                <th></th>
                <th></th>
            </tr>
            <c:forEach var="order" items="${newOrders}">
                <tr>
                    <td><a href="./librarian/user?id=${order.userId}">${order.nickName}</a></td>
                    <td>${order.author}</td>
                    <td>${order.publisher}</td>
                    <td>${order.publicationDate}</td>
                    <td>${order.orderBookCount}</td>
                    <td>
                        <form method="get" action="http://localhost:8080/library/librarian/order" >
                            <button type="submit" name="id" value="${order.orderId}">Accept</button>
                        </form>
                    </td>
                    <td>
                        <button class="cancel" type="submit" value="${order.orderId}">Cancel</button>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <h3>Active orders:</h3>
        <table>
            <tr>
                <th>User nickname</th>
                <th>Author</th>
                <th>Publisher</th>
                <th>Publication date</th>
                <th>Count</th>
                <th>Deadline</th>
                <th>Penalty</th>
                <th></th>
            </tr>
            <c:forEach var="order" items="${activeOrders}">
                <tr>
                    <td><a href="./librarian/user?id=${order.userId}">${order.nickName}</a></td>
                    <td>${order.author}</td>
                    <td>${order.publisher}</td>
                    <td>${order.publicationDate}</td>
                    <td>${order.orderBookCount}</td>
                    <td>${order.plannedReturn}</td>
                    <td>${order.penalty}</td>
                    <td>
                        <button class="return" type="submit" value="${order.orderId}">Return</button>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <h3>Expired orders:</h3>
        <table>
            <tr>
                <th>User nickname</th>
                <th>Author</th>
                <th>Publisher</th>
                <th>Publication date</th>
                <th>Count</th>
                <th>Deadline</th>
                <th>Penalty</th>
                <th></th>
            </tr>
            <c:forEach var="order" items="${expiredOrders}">
                <tr>
                    <td><a href="./librarian/user?id=${order.userId}">${order.nickName}</a></td>
                    <td>${order.author}</td>
                    <td>${order.publisher}</td>
                    <td>${order.publicationDate}</td>
                    <td>${order.orderBookCount}</td>
                    <td>${order.plannedReturn}</td>
                    <td>${order.penalty}</td>
                    <td>
                        <button class="return" type="submit" value="${order.orderId}">Return</button>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>