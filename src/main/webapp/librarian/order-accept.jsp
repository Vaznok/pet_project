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

        <title><fmt:message key="order-confirmation.msg"/></title>

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
                    <td><fmt:message key="deadline.msg"/></td>
                    <td><input id="txtDate" type="date" name="plannedReturn" size="30"></td>
                </tr>
                <tr>
                    <td><fmt:message key="penalty.msg"/></td>
                    <td><input type="number" min="0" name="penalty" size="30"></td>
                </tr>
                <input type="hidden" name="id" value="${param.id}"/>
            </table>
            <p><input type="submit" value="Done"></p>
        </form>
        <h4 style="color: red"><custom:print result='${requestScope.noValidation}'/></h4>
    </body>
</html>