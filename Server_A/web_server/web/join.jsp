<%--
  Created by IntelliJ IDEA.
  User: scopi
  Date: 2018-05-29
  Time: 오후 6:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%--
<%request.setCharacterEncoding("euc-kr");%>
<jsp:useBean id="dto" class="bean.SignupDTO"/>
<jsp:setProperty name="dto" property="*" />

<%@ page import="java.sql.*" %>
<%@ page import="bean.SignupDAO" %>

<%
    SignupDAO dao = SignupDAO.getInstance();
    dao.insert(dto);
%>
--%>


<html>
<head>
    <title>join</title>
</head>
<body>
    <h2>Sign-up Form</h2>
    <form action="joinpro.jsp" method="post">
        ID: <input type="text" name="id"/> <br />
        PASSWORD: <input type="password" name="password"/> <br />
    <!--    ACCESS-TOKEN: <input type="text" name="access_token"> <br />
        SECRET-KEY: <input type="text" name="secret_key"> <br />
        E-MAIL: <input type="text" name="email"> <br />
    -->
        <input type="submit" value="JOIN" />
    </form>


</body>
</html>
