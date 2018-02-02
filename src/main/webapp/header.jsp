<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String language = request.getLocale().toString();
    request.setAttribute("language", language);
%>

<html>
    <head>
        <style>
            <%@include file="/WEB-INF/locale.css"%>
            <%@include file="/WEB-INF/common.css"%>
        </style>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script type="text/javascript">
            $(document).ready(function() {
                $('.locale').click(function () {
                    var locale = $(this).attr('name');
                    $.ajax({
                        url: 'http://localhost:8080/library/locale',
                        type: 'POST',
                        data: {
                            locale: locale
                        },
                        success: function () {
                            location.reload();
                        },
                        error: function () {

                        }
                    });
                });
            });
        </script>
        <div class="dropdown">
            <c:choose>
                <c:when test="${language == 'ru'}">
                    <div id="dropbtnRU"><a>RU</a></div>
                </c:when>
                <c:otherwise>
                    <div id="dropbtnEN"><a>EN</a></div>
                </c:otherwise>
            </c:choose>
            <div class="dropdown-content">
                <a href="#" class="locale" name="ru">RU</a>
                <a href="#" class="locale" name="en">EN</a>
            </div>
        </div>
