package bean;

import java.sql.*;
import java.util.ArrayList;

public class LogDAO {

    private static Connection connection = null;
    private static PreparedStatement statement = null;
    private static ResultSet rs = null;

    private static LogDAO dao = new LogDAO();

    //다른 클래스에서 이 클래스의 인스턴스를 new를 통해 생성하지 못하게금
    //싱글톤 패턴 이용
    public static LogDAO getInstance(){
        return dao;
    }

    private LogDAO(){}

    public static Connection getMysql() throws Exception{

        //수정 요함
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/ticker_database?autoReconnect=true&serverTimezone=UTC";
        Connection conn = DriverManager.getConnection(url,"root","");// 비밀번호 입력

        return conn;

    }

    public ArrayList<LogDTO> select(){
        ArrayList<LogDTO> list = new ArrayList<LogDTO>();
        try{
            connection = getMysql();
            statement = connection.prepareStatement("select * from buysell;");
            rs = statement.executeQuery();

            while(rs.next()){
                LogDTO dto = new LogDTO();

                dto.setDate(rs.getInt("date")+"");
                dto.setCoin(rs.getString("coin"));
                dto.setBuy_value(rs.getInt("buy_value")+"");
                dto.setSell_value(rs.getInt("sell_value")+"");
                dto.setProfit(rs.getInt("profit")+"");

                list.add(dto);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try { rs.close(); } catch (SQLException e) {}
            try { statement.close(); } catch (SQLException e) {}
            try { connection.close(); } catch (SQLException e) {}
        }
        return list;
    }

}
