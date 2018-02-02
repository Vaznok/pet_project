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


        <title><fmt:message key="personal-account"/></title>

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
        <h2><fmt:message key="personal-account"/></h2>
        <table>
            <tr>
                <td><fmt:message key="email.msg"/>:</td>
                <td>${sessionScope.user.email}</td>
            </tr>
            <tr>
                <td><fmt:message key="nick-name.msg"/>:</td>
                <td>${sessionScope.user.nickName}</td>
            </tr>
            <tr>
                <td><fmt:message key="first-name.msg"/>:</td>
                <td>${sessionScope.user.firstName}</td>
            </tr>
            <tr>
                <td><fmt:message key="last-name.msg"/>:</td>
                <td>${sessionScope.user.lastName}</td>
            </tr>
            <tr>
                <td><fmt:message key="contact.msg"/>:</td>
                <td>${sessionScope.user.contact}</td>
            </tr>
        </table>
        <button type="submit"><fmt:message key="update.button"/></button>

        <c:if test = "${not empty newOrders}">
            <h3><fmt:message key="new-orders.msg"/>:</h3>
            <table>
                <tr>
                    <th><fmt:message key="book-name.msg"/></th>
                    <th><fmt:message key="book-author.msg"/></th>
                    <th><fmt:message key="publisher.msg"/></th>
                    <th><fmt:message key="publication-date.msg"/></th>
                    <th><fmt:message key="book-count.msg"/></th>
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
        </c:if>

        <c:if test = "${not empty activeOrders}">
            <h3><fmt:message key="active-orders.msg"/>:</h3>
            <table>
                <tr>
                    <th><fmt:message key="book-name.msg"/></th>
                    <th><fmt:message key="book-author.msg"/></th>
                    <th><fmt:message key="publisher.msg"/></th>
                    <th><fmt:message key="publication-date.msg"/></th>
                    <th><fmt:message key="book-count.msg"/></th>
                    <th><fmt:message key="received-order.msg"/></th>
                    <th><fmt:message key="deadline.msg"/></th>
                    <th><fmt:message key="penalty.msg"/></th>
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
        </c:if>
    </body>
</html>