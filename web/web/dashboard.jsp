<%--
  Created by IntelliJ IDEA.
  User: scopi
  Date: 2018-05-25
  Time: 오후 2:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<%
    if(session.getAttribute("sessionId") == null){
        response.sendRedirect("logout.jsp");
    }
%>



<html>
    <head>
        <title>Dashboard</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <style type="text/css">
            @import url('https://fonts.googleapis.com/css?family=Nanum+Gothic+Coding');
            @import url('https://fonts.googleapis.com/css?family=Anaheim|Rajdhani:500');

            * {
                margin: 0;
                padding: 0;
            }

            body{
                background-color: #e8e4d9;
            }

            #msg1{
                font-family:'Rajdhani', 'sans-serif';
                position: absolute;
                font-size: 50px;
                color: #5c3317;
                margin-top: -10px;
                margin-left: 200px;
            }

            div.menubar ul{
                list-style-type: none;
                margin: 50px;
                margin-left: 60%;
                padding: 5px;
                display: block;
            }

            div.menubar li{
                font-size: 20px;
                float: left;
                margin: 10px;
                padding: 5px;
                display: inline;
            }

            div.menubar a:link { color: #983642; text-decoration: none;}
            div.menubar a:visited { color: #983642; text-decoration: none;}
            div.menubar a:hover { color: #983642; background-color: white; text-decoration: none;}

            div.menubar:after {
                content: "";
                display: block;
                width: 1200px;
                margin: 20px auto;
                border-bottom: 2px solid white;
            }

            #msg2{
                font-family:'Rajdhani', 'sans-serif';
                position: absolute;
                font-size: 30px;
                color: #a28375;
                margin-top: 00px;
                margin-left: 200px;
            }

            #logout{
                height: 35px;
                width: 130px;
                background-color: #ab4754;
                font-family: 'Nanum Gothic', sans-serif;
                font-size: 16px;
                color: white;
                border: none;
                outline: none;
                border-radius: 5px;
                margin-top: 0px;
                margin-left: 77%;
            }


            #box{
                height: 400px;
                width: 1000px;
                background-color: white;
                border-radius: 5px;
                position: absolute;
                top: 50%;
                left: 50%;
                transform: translate(-50%,-50%);
            }

            #msg3{
                font-family:'Anaheim','sans-serif';
                position: absolute;
                margin-top: 30px;
                margin-left: 50px;
                font-size: 30px;
                color: #ab4754;
            }

            #msg4{
                font-family:'Anaheim','sans-serif';
                position: absolute;
                margin-top: 75px;
                margin-left: 52px;
                font-size: 20px;
                color: black;
            }
            #kor1{
                font-family:'Nanum Gothic Coding','sans-serif';
                position: absolute;
                margin-top: 100px;
                margin-left: 52px;
                font-size: 15px;
                color: gray;
            }

            #msg5{
                font-family:'Anaheim','sans-serif';
                position: absolute;
                margin-top: 150px;
                margin-left: 51px;
                font-size: 25px;
                color: #a28375;
            }

            #cont_name{
                font-family:'Anaheim','sans-serif';
                position: absolute;
                margin-top: 180px;
                margin-left: 51px;
                font-size: 20px;
                color: black;
            }

            #kor2{
                font-family:'Nanum Gothic Coding','sans-serif';
                position: absolute;
                margin-top: 210px;
                margin-left: 52px;
                font-size: 15px;
                color: gray;
            }

            #github{
                list-style: none;
                position: absolute;
                size: 20px;
                left: 90%;
                top: 10%;
            }

            #msg6{
                font-family:'Anaheim','sans-serif';
                position: absolute;
                margin-top: 330px;
                margin-left: 51px;
                font-size: 25px;
                color: #983642;
            }

        </style>
    </head>
    <body>
        <p id="msg1" onclick= "window.location='dashboard.jsp'">DASHBOARD</p>

        <div class="menubar">
            <ul>
                <li><a href="dashboard.jsp" style="color:#a28375">HOME</a></li>
                <li><a href="tradinglog.jsp">LOG</a></li>
                <li><a href="aipredict.jsp">AI</a></li>
                <li><a href="chageinfo.jsp">INFO</a></li>
                <li><a href="emergency.jsp">EMERGENCY</a></li>
            </ul>
        </div>

        <p id="msg2">Welcome <%= session.getAttribute("sessionId")%>:)</p>
        <button id="logout" onclick= "window.location='logout.jsp'">LOGOUT</button>

        <div id="box">
            <span id="msg3">CryptoCurrency AutoTrading System in web</span>
            <span id="msg4">2018 1st Semester KUCAP final team project</span>
            <span id="kor1">2018년 1학기 컴퓨터 응용 및 실습 기말 팀 프로젝트</span>
            <span id="msg5">Main Contributor</span>
            <span id="cont_name">LuterGS  emiling  Glacier  Randomshot</span>
            <span id="kor2">201711413 컴퓨터공학과 이관석<br>201711413 컴퓨터공학과 이유진<br>201711371 컴퓨터공학과 강지원<br>201711419 컴퓨터공학과 이호현</span>
            <a href="https://github.com/LuterGS/coinone_autotrading_system"><img id= "github" src="github_mark.png" alt="github"></a>
            <span id="msg6">If you want to see this project specificially, please visit our github page </span>
        </div>
    </body>
</html>

