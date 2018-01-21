<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/tag/welcome.tld" prefix="custom" %>
<html>
<head>
    <title>Welcome</title>
    <h2><custom:welcome name='${sessionScope.user.nickName}'/>! </h2>
</head>
    <body>
        <p>You are on the site of the best Dnepropetrovsk library. Here you can see our list of books that you can rent or read in our cozy reading room.</p>
        <p>To make order and receive a book you must register and log in to the site. Also in your personal account, you can track the deadline of renting a book.</p>
        <p><a href="./login">Log in</a></p>
        <p><a href="./check-in">Check in</a></p>
        <p><a href="./account">My account</a></p>
        <p><a href="./catalog">Book catalog</a></p>
    </body>
</html>
