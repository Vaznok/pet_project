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
<jsp:include page="header.jsp" />

        <title>${book.name}</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script type="text/javascript">
            $(document).ready(function() {
                $('.delete').click(  function () {
                    var sendBookId = $('.delete').val();
                    $.ajax({
                        url: 'http://localhost:8080/library/book',
                        type: 'DELETE',
                        data: {
                            bookId: sendBookId
                        },
                        success: function () {
                            window.location.replace("http://localhost:8080/library/catalog");
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
            <tr>
                <td><fmt:message key="book-name.msg"/>:</td>
                <td>${book.name}</td>
            </tr>
            <tr>
                <td><fmt:message key="book-author.msg"/>:</td>
                <td>${book.author}</td>
            </tr>
            <tr>
                <td><fmt:message key="publisher.msg"/>:</td>
                <td>${book.publisher}</td>
            </tr>
            <tr>
                <td><fmt:message key="publication-date.msg"/>:</td>
                <td>${book.publicationDate}</td>
            </tr>
            <tr>
                <td><fmt:message key="book-count.msg"/>:</td>
                <td>${book.count}</td>
            </tr>
        </table>
        <c:choose>
            <c:when test="${sessionScope.user.role == 'ADMINISTRATOR'}">
                <form method="post" action="http://localhost:8080/library/book">
                    <button type="submit"><fmt:message key="make-order.button"/></button><input name="bookCount" type="number" min="1" size="4"/>
                    <input name="bookId" type="hidden" value="${book.id}">
                </form>
                <button class="update" type="submit" value="${book.id}"><fmt:message key="update.button"/></button>
                <button class="delete" type="submit" value="${book.id}"><fmt:message key="delete.button"/></button>
            </c:when>
            <c:when test="${sessionScope.user.role != null}">
                <form method="post" action="http://localhost:8080/library/book">
                    <button type="submit"><fmt:message key="make-order.button"/></button><input name="bookCount" type="number" min="1" size="4"/>
                    <input name="bookId" type="hidden" value="${book.id}">
                </form>
            </c:when>
            <c:otherwise>
                <p style="color: red"><fmt:message key="no-right.msg"/></p>
                <p><a href="./login"><fmt:message key="login.msg"/></a></p>
                <p><a href="./check-in"><fmt:message key="check-in.msg"/></a></p>
            </c:otherwise>
        </c:choose>
        <h4 style="color: red"><custom:print result='${requestScope.notEnoughBooks}'/></h4>
    </body>
</html>
