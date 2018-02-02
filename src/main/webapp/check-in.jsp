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

        <title><fmt:message key="registration-form.msg"/></title>
        <h2><fmt:message key="registration-form.msg"/></h2>
    </head>
    <body>
        <form name="check-in" method="post" action="./check-in">
            <table>
                <tr>
                    <td><fmt:message key="email.msg"/>:</td>
                    <td><input type="text" name="email" size="30"></td>
                </tr>
                <tr>
                    <td><fmt:message key="password.msg"/>:</td>
                    <td><input type="password" name="password" size="30" min="6" max="18"></td>
                </tr>
                <tr>
                    <td><fmt:message key="nick-name.msg"/>:</td>
                    <td><input type="text" name="nickName" size="30" min="3" max="14"></td>
                </tr>
                <tr>
                    <td><fmt:message key="first-name.msg"/>:</td>
                    <td><input type="text" name="firstName" size="30" min="3" max="14"></td>
                </tr>
                <tr>
                    <td><fmt:message key="last-name.msg"/>:</td>
                    <td><input type="text" name="lastName" size="30" min="3" max="14"></td>
                </tr>
                <tr>
                    <td><fmt:message key="contact.msg"/>:</td>
                    <td><input type="text" name="contact" size="30" min="8" max="150"></td>
                </tr>
            </table>
            <button type="submit"><fmt:message key="done.button"/></button>
        </form>
        <h4 style="color: red"><custom:print result='${requestScope.noRegister}'/></h4>
    </body>
</html>