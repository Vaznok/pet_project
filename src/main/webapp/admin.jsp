<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>

<html>
<head>
    <title>Admin</title>
    <h2>Administration panel.</h2>
    <h2>Welcome, ${sessionScope.user.nickName}!</h2>
</head>
<body>
    <p><a href="./admin/librarian-reg">Create librarian.</a></p>
    <p><a href="./admin/librarian-del">Delete librarian.</a></p>
    <p><a href="./admin/user-block">Block or unlock user.</a></p>
    <p><a href="./catalog">Delete or update a book.</a></p>
    <p><a href="./admin/book-new">Add new book.</a></p>
</body>
</html>

