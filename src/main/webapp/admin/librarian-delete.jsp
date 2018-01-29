<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8"%>

<html>
<head>
    <title>Librarians</title>
    <h3>Librarians:</h3>
    <%--<style>
        <%@include file="/WEB-INF/librarian.css"%>
    </style>--%>
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
        <th>Librarian nickname</th>
        <th>Email</th>
        <th>First name</th>
        <th>Last name</th>
        <th>Contact</th>
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
                <button id="delete" type="submit" value="${librarian.id}">Delete</button>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>