<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/tag/print.tld" prefix="custom" %>
<html>
    <head>
        <title>Check-in form</title>
        <h2>Registration form:</h2>
        <style>
            <%@include file="/WEB-INF/check-in.css"%>
        </style>
    </head>
    <body>
        <form name="check-in" method="post" action="http://localhost:8080/library/check-in">
            <table>
                <tr>
                    <td>Email: </td>
                    <td><input type="text" name="email" size="30"></td>
                </tr>
                <tr>
                    <td>Password: </td>
                    <td><input type="password" name="password" size="30"></td>
                </tr>
                <tr>
                    <td>Nick name: </td>
                    <td><input type="text" name="nickName" size="30"></td>
                </tr>
                <tr>
                    <td>First name: </td>
                    <td><input type="text" name="firstName" size="30"></td>
                </tr>
                <tr>
                    <td>Last name: </td>
                    <td><input type="text" name="lastName" size="30"></td>
                </tr>
                <tr>
                    <td>Contact: </td>
                    <td><input type="text" name="contact" size="30"></td>
                </tr>
            </table>
            <p><input type="submit" value="Done"></p>
        </form>
        <h4 style="color: red"><custom:print result='${requestScope.noRegister}'/></h4>
    </body>
</html>