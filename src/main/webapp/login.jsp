<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/tag/login.tld" prefix="custom" %>
<html>
<head>
    <title>Login form.</title>
</head>
    <body>
        <form name="example" method="post" action="http://localhost:8080/library/login">
            <h3>Login</h3> <input type="text" name="username" size="30">
            <h3>Password</h3> <input type="password" name="password" size="30">
            <p><input type="submit" value="Submit"></p>
        </form>
        <h2 style="color: red"><custom:login result='${requestScope.noSuchUser}'/></h2>
    </body>
</html>
