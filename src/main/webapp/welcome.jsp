<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/tag/welcome.tld" prefix="custom" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String language = request.getLocale().toString();
    request.setAttribute("language", language);
%>
<fmt:requestEncoding value="UTF-8" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="i18n.messages" />
<jsp:include page="header.jsp" />

        <title><fmt:message key="welcome.msg"/> </title>
    </head>
    <body>
        <h2><fmt:message key="welcome.msg" />, <custom:welcome name='${sessionScope.user.nickName}' lang='${language}'/>! </h2>
        <p style="width: 1000px"><fmt:message key="library.description"/></p>
        <p><a href="./login"><fmt:message key="login.msg"/></a></p>
        <p><a href="./check-in"><fmt:message key="check-in.msg"/></a></p>
        <p><a href="./account"><fmt:message key="personal-account"/></a></p>
        <p><a href="./catalog"><fmt:message key="catalog.msg"/></a></p>
        <c:choose>
            <c:when test="${sessionScope.user.role == 'ADMINISTRATOR'}">
                <p><a href="./admin"><fmt:message key="admin-panel.msg"/></a></p>
                <p><a href="./librarian"><fmt:message key="librarian-panel.msg"/></a></p>
            </c:when>
            <c:when test="${sessionScope.user.role == 'LIBRARIAN'}">
                <p><a href="./librarian"><fmt:message key="librarian-panel.msg"/></a></p>
            </c:when>
        </c:choose>
    </body>
</html>
