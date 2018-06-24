<%--
  Created by IntelliJ IDEA.
  User: scopi
  Date: 2018-06-04
  Time: 오후 4:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="bean.SignupDAO" %>

<jsp:useBean id="dto" class="bean.SignupDTO"/>
<jsp:setProperty name="dto" property="*"/>


<html>
<head>
    <!-- 가입폼 작성 후 확인 및 가입 완료 안내 페이지 -->
    <title>Join Process</title>
    <link rel="stylesheet" href="joinpro_main.css"/>
</head>
<body>
    <% request.setCharacterEncoding("euc-kr"); %>
    <div id="msg1">
        <a href="login.html"><h2>CryptoCurrency AutoTrading System</h2></a>
    </div>
    <div id="box">
        <div id="main"></div>
        <div id="signupform">
            <h1>가입완료</h1>

    <%
        SignupDAO dao = SignupDAO.getInstance();
        dao.insert(dto);
    %>
            <!--
            <p>ID <style color="black"></>style></p>
            <p>Password <style color="black"></>style></p>
            <p>Access Token <style color="black"></>style></p>
            <p>Secret Key <style color="black"></>style></p>
            <p>Email <style color="black"></>style></p>
            -->
            <button onclick= "window.location='index.jsp'"/>BACK
        </div>
        <div id="welcom_msg">Welcome to our service :)</div>

        <!--<button id="signup_btn">BACK</button>-->

        <div id="msg2">
            <h2>2018 1st Semester KUCAP final team project</h2>
        </div>
    </div>

</body>
</html>
