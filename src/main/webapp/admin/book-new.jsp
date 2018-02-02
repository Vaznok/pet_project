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

        <title><fmt:message key="add-book.msg"/></title>
        <h2><fmt:message key="add-book.msg"/>:</h2>
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
        <form name="book-new" method="post" action="./book-new">
            <table>
                <tr>
                    <td><fmt:message key="book-name.msg"/>:</td>
                    <td><input type="text" name="bookName" size="30" min="3" max="50"></td>
                </tr>
                <tr>
                    <td><fmt:message key="book-author.msg"/>:</td>
                    <td><input type="text" name="author" size="30" min="3" max="30"></td>
                </tr>
                <tr>
                    <td><fmt:message key="publisher.msg"/>:</td>
                    <td><input type="text" name="publisher" size="30" min="3" max="20"></td>
                </tr>
                <tr>
                    <td><fmt:message key="publication-date.msg"/>:</td>
                    <td><input id="txtDate" type="date" name="publicationDate" size="30"></td>
                </tr>
                <tr>
                    <td><fmt:message key="book-count.msg"/>:</td>
                    <td><input type="number" name="count" size="30" min="0" max="10000"></td>
                </tr>
            </table>
            <button type="submit"><fmt:message key="done.button"/></button>
        </form>
        <h4 style="color: red"><custom:print result='${requestScope.noAdd}'/></h4>
        <h4 style="color: green"><custom:print result='${requestScope.add}'/></h4>
    </body>
</html>