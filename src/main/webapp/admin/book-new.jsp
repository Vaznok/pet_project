<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/tag/print.tld" prefix="custom" %>
<html>
<head>
    <title>Book creation</title>
    <h2>Add new book:</h2>
    <%--<style>
        <%@include file="/WEB-INF/check-in.css"%>
    </style>--%>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script type="text/javascript">
        $(function(){
            var dtToday = new Date();

            var month = dtToday.getMonth() + 1;
            var day = dtToday.getDate();
            var year = dtToday.getFullYear();
            if(month < 10)
                month = '0' + month.toString();
            if(day < 10)
                day = '0' + day.toString();

            var maxDate = year + '-' + month + '-' + day;
            alert(maxDate);
            $('#txtDate').attr('max', maxDate);
        });
    </script>
</head>
<body>
<form name="book-new" method="post" action="http://localhost:8080/library/admin/book-new">
    <table>
        <tr>
            <td>Book name: </td>
            <td><input type="text" name="bookName" size="30" min="3" max="50"></td>
        </tr>
        <tr>
            <td>Author: </td>
            <td><input type="text" name="author" size="30" min="3" max="30"></td>
        </tr>
        <tr>
            <td>Publisher: </td>
            <td><input type="text" name="publisher" size="30" min="3" max="20"></td>
        </tr>
        <tr>
            <td>Publication date: </td>
            <td><input id="txtDate" type="date" name="publicationDate" size="30"></td>
        </tr>
        <tr>
            <td>Count: </td>
            <td><input type="number" name="count" size="30" min="0" max="10000"></td>
        </tr>
    </table>
    <p><input type="submit" value="Done"></p>
</form>
    <h4 style="color: red"><custom:print result='${requestScope.noAdd}'/></h4>
    <h4 style="color: green"><custom:print result='${requestScope.add}'/></h4>
</body>
</html>