<%--
  Created by IntelliJ IDEA.
  User: scopi
  Date: 2018-06-04
  Time: 오후 7:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page  import="java.sql.*" %>
<%@ page import="javax.naming.*" %>
<%@ page import="javax.sql.DataSource" %>
<%
    Connection conn = null;
    try{
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306?autoReconnect=true&serverTimezone=UTC","root","niconiconi990!");

        /*
        Context init = new InitialContext();
        Context env = (Context)init.lookup("java:comp/env");
        DataSource ds = (DataSource)init.lookup("jdbc/mysql");
        conn = ds.getConnection();
        */

        out.println("<h3>연결성공</h3>");
    }catch(Exception e){
        out.println("<h3>연결실패</h3>");
        e.printStackTrace();
    }
%>
<html>
<head>
    <title>dbcp test</title>
</head>
<body>

</body>
</html>
