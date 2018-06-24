<%--
  Created by IntelliJ IDEA.
  User: scopi
  Date: 2018-06-04
  Time: 오후 6:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    session.invalidate();
    response.sendRedirect("index.jsp");
%>
<html>
<head>
    <title>Logout</title>
</head>
<body>

</body>
</html>
