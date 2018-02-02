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

        <title><fmt:message key="admin-panel.msg"/></title>
        <h2><fmt:message key="admin-panel.msg"/></h2>
    </head>
    <body>
        <p><a href="./admin/librarian-reg"><fmt:message key="create-librarian.msg"/></a></p>
        <p><a href="./admin/librarian-del"><fmt:message key="delete-librarian"/></a></p>
        <p><a href="./admin/user-block"><fmt:message key="block-user.msg"/>.</a></p>
        <p><a href="./catalog"><fmt:message key="del-update-book.msg"/>.</a></p>
        <p><a href="./admin/book-new"><fmt:message key="add-book.msg"/>.</a></p>
    </body>
</html>

