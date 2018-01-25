<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/tag/print.tld" prefix="custom" %>
<html>
<head>
    <title>Login form.</title>
</head>
    <body>
        <form name="login" method="post" action="http://localhost:8080/library/login">
            <table>
                <tr>
                    <td>Email: </td>
                    <td><input type="text" name="email" size="30"></td>
                </tr>
                <tr>
                    <td>Password: </td>
                    <td><input type="password" name="password" size="30"></td>
                </tr>
            </table>
            <p><input type="submit" value="Login"></p>
        </form>
        <h4 style="color: red"><custom:print result='${requestScope.noLogin}'/></h4>
    </body>
</html>
