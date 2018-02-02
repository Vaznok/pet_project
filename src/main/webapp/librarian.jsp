<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String language = request.getLocale().toString();
    request.setAttribute("language", language);
%>
<fmt:requestEncoding value="UTF-8" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="i18n.messages" />
<jsp:include page="header.jsp" />

        <title>Orders</title>
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
        <c:if test = "${not empty newOrders}">
            <h3><fmt:message key="new-orders.msg"/>:</h3>
            <table>
                <tr>
                    <th><fmt:message key="nick-name.msg"/></th>
                    <th><fmt:message key="book-name.msg"/></th>
                    <th><fmt:message key="book-author.msg"/></th>
                    <th><fmt:message key="publisher.msg"/></th>
                    <th><fmt:message key="publication-date.msg"/></th>
                    <th><fmt:message key="book-count.msg"/></th>
                    <th></th>
                    <th></th>
                </tr>
                <c:forEach var="order" items="${newOrders}">
                    <tr>
                        <td><a href="./librarian/user?id=${order.userId}">${order.nickName}</a></td>
                        <td>${order.bookName}</td>
                        <td>${order.author}</td>
                        <td>${order.publisher}</td>
                        <td>${order.publicationDate}</td>
                        <td>${order.orderBookCount}</td>
                        <td>
                            <form method="get" action="http://localhost:8080/library/librarian/order" >
                                <button type="submit" name="id" value="${order.orderId}"><fmt:message key="accept-order.button"/></button>
                            </form>
                        </td>
                        <td>
                            <button class="cancel" type="submit" value="${order.orderId}"><fmt:message key="cancel-order.button"/></button>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
        <c:if test = "${not empty activeOrders}">
            <h3><fmt:message key="active-orders.msg"/>:</h3>
            <table>
                <tr>
                    <th><fmt:message key="nick-name.msg"/></th>
                    <th><fmt:message key="book-name.msg"/></th>
                    <th><fmt:message key="book-author.msg"/></th>
                    <th><fmt:message key="publisher.msg"/></th>
                    <th><fmt:message key="publication-date.msg"/></th>
                    <th><fmt:message key="book-count.msg"/></th>
                    <th><fmt:message key="deadline.msg"/></th>
                    <th><fmt:message key="penalty.msg"/></th>
                    <th></th>
                </tr>
                <c:forEach var="order" items="${activeOrders}">
                    <tr>
                        <td><a href="./librarian/user?id=${order.userId}">${order.nickName}</a></td>
                        <td>${order.bookName}</td>
                        <td>${order.author}</td>
                        <td>${order.publisher}</td>
                        <td>${order.publicationDate}</td>
                        <td>${order.orderBookCount}</td>
                        <td>${order.plannedReturn}</td>
                        <td>${order.penalty}</td>
                        <td>
                            <button class="return" type="submit" value="${order.orderId}"><fmt:message key="return-order.button"/></button>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
        <c:if test = "${not empty expiredOrders}">
            <h3><fmt:message key="expired-orders.msg"/>:</h3>
            <table>
                <tr>
                    <th><fmt:message key="nick-name.msg"/></th>
                    <th><fmt:message key="book-name.msg"/></th>
                    <th><fmt:message key="book-author.msg"/></th>
                    <th><fmt:message key="publisher.msg"/></th>
                    <th><fmt:message key="publication-date.msg"/></th>
                    <th><fmt:message key="book-count.msg"/></th>
                    <th><fmt:message key="deadline.msg"/></th>
                    <th><fmt:message key="penalty.msg"/></th>
                    <th></th>
                </tr>
                <c:forEach var="order" items="${expiredOrders}">
                    <tr>
                        <td><a href="./librarian/user?id=${order.userId}">${order.nickName}</a></td>
                        <td>${order.bookName}</td>
                        <td>${order.author}</td>
                        <td>${order.publisher}</td>
                        <td>${order.publicationDate}</td>
                        <td>${order.orderBookCount}</td>
                        <td>${order.plannedReturn}</td>
                        <td>${order.penalty}</td>
                        <td>
                            <button class="return" type="submit" value="${order.orderId}"><fmt:message key="return-order.button"/></button>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
    </body>
</html>