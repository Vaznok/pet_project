<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>

<html>
    <head>
        <title>Book</title>
        <%--<style>
            <%@include file="/WEB-INF/librarian.css"%>
        </style>--%>
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
        <h3>${book.name}</h3>
        <h4>${book.author}</h4>
        <h4>${book.publisher}</h4>
        <h4>${book.publicationDate}</h4>
        <c:choose>
            <c:when test="${sessionScope.user.role == 'ADMINISTRATOR'}">
                <form method="post" action="http://localhost:8080/library/book">
                    <input name="bookCount" type="number" min="1" size="4"/><input type="submit" value="Make order"/>
                    <input name="bookId" type="hidden" value="${book.id}">
                </form>
                <input type="submit" value="Update">
                <button class="delete" type="submit" value="${book.id}">Delete</button>
            </c:when>
            <c:when test="${sessionScope.user.role != null}">
                <form method="post" action="http://localhost:8080/library/book">
                    <input name="bookCount" type="number" min="1" size="4"/><input type="submit" value="Make order"/>
                    <input name="bookId" type="hidden" value="${book.id}">
                </form>
            </c:when>
            <c:otherwise>
            </c:otherwise>
        </c:choose>
        <h4 style="color: red"><custom:print result='${requestScope.notEnoughBooks}'/></h4>
    </body>
</html>
