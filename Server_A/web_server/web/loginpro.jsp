<%--
  Created by IntelliJ IDEA.
  User: scopi
  Date: 2018-05-25
  Time: 오후 2:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="bean.SignupDAO" %>
<%
    String id = request.getParameter("id");
    String password = request.getParameter("password");

    SignupDAO dao = SignupDAO.getInstance();
    boolean check = dao.loginCheck(id,password);

    String redirectUrl = "index.jsp"; // 인증 실패시 재요청 될 url

    if(check){
        session.setAttribute("sessionId", id);
        //session.setAttribute("sessionPw", password);
        response.sendRedirect("dashboard.jsp");
    }else{
        response.sendRedirect(redirectUrl);
    }

%>
<html>
<head>
    <title>Login Process</title>
</head>
<body>
</body>
</html>