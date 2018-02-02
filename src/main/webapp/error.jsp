<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String language = request.getLocale().toString();
    request.setAttribute("language", language);
%>
<fmt:requestEncoding value="UTF-8" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="i18n.messages" />

<html>
    <head>
        <title><fmt:message key="error.msg"/></title>
    </head>
    <body>
        <h3><fmt:message key="error-message.msg"/></h3>
    </body>
</html>
