<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8"%>

<html>
    <head>
        <title>Orders</title>
        <h3>Orders:</h3>
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
    </head>
    <body>
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
            <c:forEach var="view" items="${views}">
                <tr>
                    <td><a href="./book?id=${view.userId}">${view.nickName}</a></td>
                    <td>${view.author}</td>
                    <td>${view.publisher}</td>
                    <td>${view.publicationDate}</td>
                    <td>${view.orderBookCount}</td>
                    <td>
                        <form method="get" action="http://localhost:8080/library/librarian/order" >
                            <button type="submit" name="id" value="${view.orderId}">Accept</button>
                        </form>
                    </td>
                    <td>
                        <button class="cancel" type="submit" value="${view.orderId}">Cancel</button>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>