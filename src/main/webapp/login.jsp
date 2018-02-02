<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/tag/print.tld" prefix="custom" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String language = request.getLocale().toString();
    request.setAttribute("language", language);
%>
<fmt:requestEncoding value="UTF-8" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="i18n.messages" />
<jsp:include page="header.jsp" />

        <title><fmt:message key="login.msg"/>:</title>
    </head>
    <body>
        <form name="login" method="post" action="./login">
            <table>
                <tr>
                    <td><fmt:message key="email.msg"/>:</td>
                    <td><input type="text" name="email" size="30"></td>
                </tr>
                <tr>
                    <td><fmt:message key="password.msg"/>:</td>
                    <td><input type="password" name="password" size="30"></td>
                </tr>
            </table>
            <button type="submit"><fmt:message key="login.msg"/></button>
        </form>
        <h4 style="color: red"><custom:print result='${requestScope.noLogin}'/></h4>
    </body>
</html>