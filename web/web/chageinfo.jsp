<%--
  Created by IntelliJ IDEA.
  User: scopi
  Date: 2018-06-06
  Time: 오후 4:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="coinone.Encryptor" %>
<%@ page import="coinone.Getuserinfo" %>
<%@ page import="coinone.Getbalance" %>
<%@ page import="coinone.Parser" %>

<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- bootstrap cdn-->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

        <title>Change Information</title>
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

            #balance{
                font-family:'Rajdhani', 'sans-serif';
                position: absolute;
                font-size: 25px;
                color: #483d40;
                margin-top: 5%;
                margin-left: 20%;

            }

            #box1{
                height: 520px;
                width: 350px;
                background-color: white;
                border-radius: 5px;
                position: absolute;
                top: 58%;
                left: 30%;
                transform: translate(-50%,-50%);
            }

            #userinfo{
                font-family:'Rajdhani', 'sans-serif';
                position: absolute;
                font-size: 25px;
                color: white;
                margin-top: 4%;
                margin-left: 35%;

            }

            #box2{
                height: 520px;
                width: 600px;
                background-color: #483d40;
                border-radius: 5px;
                position: absolute;
                top: 58%;
                left: 65%;
                transform: translate(-50%,-50%);
            }

            .container {
                width: 330px;
                max-width: none !important;
            }

            #box2_con{
                top: 25%;
                left: 15%;
                font-family:'Rajdhani', 'sans-serif';
                position: absolute;
                font-size: 25px;
                color: white;
            }

        </style>
    </head>
    <body>
    <p id="msg1" onclick= "window.location='changeinfo.jsp'">INFORMATION</p>

    <div class="menubar">
        <ul>
            <li><a href="dashboard.jsp" >HOME</a></li>
            <li><a href="tradinglog.jsp">LOG</a></li>
            <li><a href="aipredict.jsp">AI</a></li>
            <li><a href="chageinfo.jsp" style="color:#a28375">INFO</a></li>
            <li><a href="emergency.jsp">EMERGENCY</a></li>
        </ul>
    </div>

    <p id="msg2">Welcome <%= session.getAttribute("sessionId")%>:)</p>
    <button id="logout" onclick= "window.location='logout.jsp'">LOGOUT</button>

    <div id="box1">
        <p id="balance">Balance Information</p>
        <div class="container">
            <div class="col-md-4" style="height:70px"></div>
            <table id="table" class="table table-condensed">
                <thead>
                <tr>
                    <th>Coin</th>
                    <th>Avail</th>
                    <th>Balance</th>
                </tr>
                </thead>
                <tbody>

                <%
                    String str = Getbalance.get_balance();
                    String[][] balance = Parser.balanceParse(str);
                %>


                <tr>
                    <td>LTC</td>
                    <td><%=balance[0][0]%></td>
                    <td><%=balance[0][1]%></td>

                </tr>
                <tr>
                    <td>BCH</td>
                    <td><%=balance[1][0]%></td>
                    <td><%=balance[1][1]%></td>
                </tr>
                <tr>
                    <td>EOS</td>
                    <td><%=balance[2][0]%></td>
                    <td><%=balance[2][1]%></td>
                </tr>
                <tr>
                    <td>QTUM</td>
                    <td><%=balance[3][0]%></td>
                    <td><%=balance[3][1]%></td>
                </tr>
                <tr>
                    <td>KRW</td>
                    <td><%=balance[4][0]%></td>
                    <td><%=balance[4][1]%></td>
                </tr>
                <tr>
                    <td>IOTA</td>
                    <td><%=balance[5][0]%></td>
                    <td><%=balance[5][1]%></td>
                </tr>
                <tr>
                    <td>ETC</td>
                    <td><%=balance[6][0]%></td>
                    <td><%=balance[6][1]%></td>
                </tr>
                <tr>
                    <td>BTG</td>
                    <td><%=balance[7][0]%></td>
                    <td><%=balance[7][1]%></td>
                </tr>
                <tr>
                    <td>BTC</td>
                    <td><%=balance[8][0]%></td>
                    <td><%=balance[8][1]%></td>
                </tr>
                <tr>
                    <td>OMG</td>
                    <td><%=balance[9][0]%></td>
                    <td><%=balance[9][1]%></td>
                </tr>
                <tr>
                    <td>ETH</td>
                    <td><%=balance[10][0]%></td>
                    <td><%=balance[10][1]%></td>
                </tr>
                <tr>
                    <td>XRP</td>
                    <td><%=balance[11][0]%></td>
                    <td><%=balance[11][1]%></td>
                </tr>


            </table>
        </div>
    </div>
    <div id="box2">
        <p id="userinfo">USER Information</p>
        <%
            String usrstr = Getuserinfo.get_info();
            String[] info = Parser.usrinfoParse(usrstr);
        %>
        <div id="box2_con">
            <p id="security">securityLevel: <%=info[0]%></p>
            <p id="bank_depositor">depositor: <%=info[1]%></p>
            <p id="bank_code">BankCode: <%=info[2]%></p>
            <p id="bank_accnum">AccountNumber: <%=info[3]%></p>
            <p id="email_email">Email: <%=info[4]%></p>
            <p id="mobile_num">PhoneNumber: <%=info[5]%></p>
        </div>
    </div>

    </body>
</html>