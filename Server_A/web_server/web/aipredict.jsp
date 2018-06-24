<%@ page import="bean.AIresultDAO" %>
<%@ page import="bean.AIresultDTO" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: scopi
  Date: 2018-06-06
  Time: 오후 4:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- bootstrap cdn-->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

        <title>AI Prediction</title>
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
        </style>
    </head>
    <body>
        <% request.setCharacterEncoding("euc-kr"); %>
        <p id="msg1" onclick= "window.location='aipredict.jsp'">AI Prediction</p>

        <div class="menubar">
            <ul>
                <li><a href="dashboard.jsp" >HOME</a></li>
                <li><a href="tradinglog.jsp">LOG</a></li>
                <li><a href="aipredict.jsp" style="color:#a28375">AI</a></li>
                <li><a href="chageinfo.jsp">INFO</a></li>
                <li><a href="emergency.jsp">EMERGENCY</a></li>
            </ul>
        </div>

        <p id="msg2">Welcome <%= session.getAttribute("sessionId")%>:)</p>
        <button id="logout" onclick= "window.location='logout.jsp'">LOGOUT</button>

        <div class="container">
            <div class="col-xs-12" style="height:60px;"></div>

            <table id="table" class="table table-striped">
                <thead>
                    <tr>
                        <th>Highest Coin</th>
                        <th>Highest Value</th>
                        <th>EOS</th>
                        <th>BCH</th>
                        <th>QTUM</th>
                        <th>IOTA</th>
                        <th>LTC</th>
                        <th>ETC</th>
                        <th>BTG</th>
                        <th>BTC</th>
                        <th>OMG</th>
                        <th>ETH</th>
                        <th>XRP</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        AIresultDAO dao = AIresultDAO.getInstance();
                        ArrayList<AIresultDTO> list = dao.select();
                        for(AIresultDTO dto : list) {
                            if(dto.getHighest_coin() == null) continue;
                    %>
                    <tr>
                        <td><%=dto.getHighest_coin()%></td>
                        <td><%=dto.getHighest_value()%></td>
                        <td><%=dto.getEos()%></td>
                        <td><%=dto.getBch()%></td>
                        <td><%=dto.getQtum()%></td>
                        <td><%=dto.getIota()%></td>
                        <td><%=dto.getLtc()%></td>
                        <td><%=dto.getEtc()%></td>
                        <td><%=dto.getBtg()%></td>
                        <td><%=dto.getBtc()%></td>
                        <td><%=dto.getOmg()%></td>
                        <td><%=dto.getEth()%></td>
                        <td><%=dto.getXrp()%></td>
                    </tr>
                        <% } %>
            </table>
        </div>

    </body>
</html>