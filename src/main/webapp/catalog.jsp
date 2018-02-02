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

    <title><fmt:message key="catalog.msg"/></title>
    <h3><fmt:message key="catalog.msg"/>:</h3>
</head>
    <body>
        <table>
            <tr>
                <th><fmt:message key="book-name.msg"/></th>
                <th><fmt:message key="book-author.msg"/></th>
                <th><fmt:message key="publisher.msg"/></th>
                <th><fmt:message key="publication-date.msg"/></th>
                <th><fmt:message key="book-count.msg"/></th>
            </tr>
            <c:forEach var="book" items="${books}">
                <tr>
                    <td><a href="./book?id=${book.id}">${book.name}</a></td>
                    <td>${book.author}</td>
                    <td>${book.publisher}</td>
                    <td>${book.publicationDate}</td>
                    <td>${book.count}</td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>