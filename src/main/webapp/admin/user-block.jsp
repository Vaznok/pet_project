<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8"%>

<html>
<head>
    <title>Block user</title>
    <h3>Users:</h3>
    <%--<style>
        <%@include file="/WEB-INF/librarian.css"%>
    </style>--%>
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
<table>
    <h2>Blocked users:</h2>
    <tr>
        <th>User nickname</th>
        <th>Email</th>
        <th>First name</th>
        <th>Last name</th>
        <th>Contact</th>
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
                <button class="unblock" type="submit" value="${blockUser.id}">Unblock</button>
            </td>
        </tr>
    </c:forEach>
</table>

<table>
    <h2>Unblocked users:</h2>
    <tr>
        <th>User nickname</th>
        <th>Email</th>
        <th>First name</th>
        <th>Last name</th>
        <th>Contact</th>
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
                    <button name="blockUserId" type="submit" value="${noBlockUser.id}">Block</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>