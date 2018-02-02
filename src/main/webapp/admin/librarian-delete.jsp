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

    <title><fmt:message key="librarian-list.msg"/></title>
    <h3><fmt:message key="librarian-list.msg"/>:</h3>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $('#delete').click(  function () {
                var librarianId = $('#delete').val();
                $.ajax({
                    url: 'http://localhost:8080/library/admin/librarian-del',
                    type: 'DELETE',
                    data: {
                        librarianId: librarianId
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
<table>
    <tr>
        <th><fmt:message key="nick-name.msg"/></th>
        <th><fmt:message key="email.msg"/></th>
        <th><fmt:message key="first-name.msg"/></th>
        <th><fmt:message key="last-name.msg"/></th>
        <th><fmt:message key="contact.msg"/></th>
        <th></th>
    </tr>
    <c:forEach var="librarian" items="${librarians}">
        <tr>
            <td>${librarian.nickName}</td>
            <td>${librarian.email}</td>
            <td>${librarian.firstName}</td>
            <td>${librarian.lastName}</td>
            <td>${librarian.contact}</td>
            <td>
                <button id="delete" type="submit" value="${librarian.id}"><fmt:message key="delete.button"/></button>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>