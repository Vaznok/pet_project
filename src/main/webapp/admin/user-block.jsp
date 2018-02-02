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

        <title><fmt:message key="users-list.msg"/></title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script type="text/javascript">
            $(document).ready(function() {
                $('.unblock').click(function () {
                    var userId = $('.unblock').val();
                    $.ajax({
                        url: 'http://localhost:8080/library/admin/user-block',
                        type: 'PUT',
                        data: {
                            unblockUserId: userId
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
        <c:if test="${not empty blockUsers}">
            <table>
                <h2><fmt:message key="blocked-users.msg"/>:</h2>
                <tr>
                    <th><fmt:message key="nick-name.msg"/></th>
                    <th><fmt:message key="email.msg"/></th>
                    <th><fmt:message key="first-name.msg"/></th>
                    <th><fmt:message key="last-name.msg"/></th>
                    <th><fmt:message key="contact.msg"/></th>
                    <th></th>
                </tr>
                <c:forEach var="blockUser" items="${blockUsers}">
                    <tr>
                        <td>${blockUser.nickName}</td>
                        <td>${blockUser.email}</td>
                        <td>${blockUser.firstName}</td>
                        <td>${blockUser.lastName}</td>
                        <td>${blockUser.contact}</td>
                        <td>
                            <button class="unblock" type="submit" value="${blockUser.id}"><fmt:message key="unblock.button"/></button>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>

        <c:if test="${not empty noBlockUsers}">
            <table>
                <h2><fmt:message key="unblocked-users.msg"/>:</h2>
                <tr>
                    <th><fmt:message key="nick-name.msg"/></th>
                    <th><fmt:message key="email.msg"/></th>
                    <th><fmt:message key="first-name.msg"/></th>
                    <th><fmt:message key="last-name.msg"/></th>
                    <th><fmt:message key="contact.msg"/></th>
                    <th></th>
                </tr>
                <c:forEach var="noBlockUser" items="${noBlockUsers}">
                    <tr>
                        <td>${noBlockUser.nickName}</td>
                        <td>${noBlockUser.email}</td>
                        <td>${noBlockUser.firstName}</td>
                        <td>${noBlockUser.lastName}</td>
                        <td>${noBlockUser.contact}</td>
                        <td>
                            <form method="post" action="http://localhost:8080/library/admin/user-block">
                                <button name="blockUserId" type="submit" value="${noBlockUser.id}"><fmt:message key="block.button"/></button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
    </body>
</html>