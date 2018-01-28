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
            /* attach a submit handler to the form */
            $("#order").submit(function(event) {

                /* stop form from submitting normally */
                event.preventDefault();

                /* get the action attribute from the <form action=""> element */
                var $form = $( this ),
                    url = $form.attr( 'action' );

                /* Send the data using post with element id name and name2*/
                var posting = $.post( url, { plannedReturn: $('#plannedReturn').val(), penalty: $('#penalty').val(), orderId: $('#orderId').val()} );

                /* Alerts the results */
                posting.done(function( data ) {
                    alert('success');
                });
            });
        </script>
    </head>
    <body>
        <form name="order" method="post" action="http://localhost:8080/library/librarian/order">
            <table>
                <tr>
                    <td>Planned return: </td>
                    <td><input type="date" name="plannedReturn" size="30"></td>
                </tr>
                <tr>
                    <td>Penalty: </td>
                    <td><input type="text" name="penalty" size="30"></td>
                </tr>
                <input type="hidden" name="id" value="${orderId}"/>
            </table>
            <p><input type="submit" value="Done"></p>
        </form>
        <h4 style="color: red"><custom:print result='${requestScope.noValidation}'/></h4>
    </body>
</html>