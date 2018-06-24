<%--
  Created by IntelliJ IDEA.
  User: scopi
  Date: 2018-05-25
  Time: 오후 2:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
      <meta charset="UTF-8"/>
      <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link rel="stylesheet" href="index_main.css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script src="index_jquery.js"></script>
  </head>
  <body>
      <div id="msg1">
          <a href="index.jsp"><h2>CryptoCurrency AutoTrading System</h2></a>
      </div>
      <div id="box">
        <div id="main"></div>
        <div id="loginform">
          <h1>LOGIN</h1>
            <form action="loginpro.jsp" method="post">
              <input type="text" name="id" placeholder="ID"/><br>
              <input type="password" name="password" placeholder="Password" /><br>
              <button>LOGIN</button>
            </form>
        </div>
        <div id="signupform">
          <h1>SIGN UP</h1>
            <form action="joinpro.jsp" method="post">
              <input type="text" name="id" placeholder="ID"/><br>
              <input type="password" name="password" placeholder="Password" /><br>
              <input type="text" name="access_token" placeholder="Access Token" /><br>
              <input type="text" name="secret_key" placeholder="Secret Key" /><br>
              <input type="email" name="email" placeholder="Email" /><br>
              <button>SIGN UP</button>
            </form>
        </div>

        <div id="login_msg">Have an account?</div>
        <div id="signup_msg">Don't have an account?</div>

        <button id="login_btn">LOGIN</button>
        <button id="signup_btn">SIGN UP</button>

        <div id="msg2">
          <h2>2018 1st Semester KUCAP final team project</h2>
        </div>
      </div>
  </body>
</html>


<%--
<html>
  <head>
    <meta charset="utf-8">
    <title>index</title>
  </head>
  <body>
    <h2>WELCOME TEST</h2>
    <form action="loginpro.jsp" method="post">
      ID <input type="text" name="id" /> <br/>
      PW <input type="password" name="password"><br/>
      <input type="submit" value="login" />
      <input type="button" value="join" onclick="window.location='join.jsp'"/>
    </form>
  </body>
</html>

%>
<%--
<% if(session.getAttribute("sessionId") == null) { %>
<html>
  <head>
    <title>index</title>
  </head>
  <body>
    <h2>WELCOME TEST</h2>
    <form action="loginpro.jsp" method="post">
      ID <input type="text" name="id" /> <br/>
      PW <input type="password" name="password"><br/>
      <input type="submit" value="login" />
      <input type="button" value="join" onclick="window.location='join.jsp'"/>
    </form>
<% }else{ %>
  <h2>WELCOME BACK <%= session.getAttribute("sessionId")%>.</h2>
  <input type="button" value="logout" onclick="window.location='logout.jsp'" />
  <% } %>
  </body>
</html>
--%>
