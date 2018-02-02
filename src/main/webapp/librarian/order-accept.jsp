<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/tag/print.tld" prefix="custom" %>
<html>
    <head>
        <title>Order Confirmation</title>
        <h2>Order Confirmation:</h2>
        <style>
            <%@include file="/WEB-INF/order-accept.css"%>
        </style>
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

                var minDate = year + '-' + month + '-' + day;
                alert(minDate);
                $('#txtDate').attr('min', minDate);
            });
        </script>
    </head>
    <body>
        <form name="order" method="post" action="http://localhost:8080/library/librarian/order">
            <table>
                <tr>
                    <td>Planned return: </td>
                    <td><input id="txtDate" type="date" name="plannedReturn" size="30"></td>
                </tr>
                <tr>
                    <td>Penalty: </td>
                    <td><input type="number" min="0" name="penalty" size="30"></td>
                </tr>
                <input type="hidden" name="id" value="${param.id}"/>
            </table>
            <p><input type="submit" value="Done"></p>
        </form>
        <h4 style="color: red"><custom:print result='${requestScope.noValidation}'/></h4>
    </body>
</html>