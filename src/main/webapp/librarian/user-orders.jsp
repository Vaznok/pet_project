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
<jsp:include page="/header.jsp" />

    <title><fmt:message key="client-history.msg"/></title>
    </head>
    <body>
        <table>
            <h2><fmt:message key="client-history.msg"/>:</h2>
            <tr>
                <th><fmt:message key="book-name.msg"/></th>
                <th><fmt:message key="book-author.msg"/></th>
                <th><fmt:message key="publisher.msg"/></th>
                <th><fmt:message key="publication-date.msg"/></th>
                <th><fmt:message key="book-count.msg"/></th>
                <th><fmt:message key="received-order.msg"/></th>
                <th><fmt:message key="deadline.msg"/></th>
                <th><fmt:message key="penalty.msg"/></th>
                <th><fmt:message key="return-date.msg"/></th>
                <th><fmt:message key="order-status.msg"/></th>
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
