package bean;

import java.sql.*;
import java.util.ArrayList;

public class AIresultDAO {

    private static Connection connection = null;
    private static PreparedStatement statement = null;
    private static ResultSet rs = null;

    private static AIresultDAO dao = new AIresultDAO();

    //다른 클래스에서 이 클래스의 인스턴스를 new를 통해 생성하지 못하게금
    //싱글톤 패턴 이용
    public static AIresultDAO getInstance(){
        return dao;
    }

    private AIresultDAO(){}


    public static Connection getMysql() throws Exception{

        //수정 요함
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/ticker_database?autoReconnect=true&serverTimezone=UTC";
        Connection conn = DriverManager.getConnection(url,"root","niconiconi990!");

        return conn;

    }

    public ArrayList<AIresultDTO> select(){
        ArrayList<AIresultDTO> list = new ArrayList<AIresultDTO>();
        try{
            connection = getMysql();
            statement = connection.prepareStatement("select * from ai_result;");
            rs = statement.executeQuery();

            while(rs.next()){
                AIresultDTO dto = new AIresultDTO();

                dto.setDate(rs.getInt("Date")+"");
                dto.setHighest_coin(rs.getString("highest_coin"));
                dto.setHighest_value(rs.getFloat("highest_value")+"");
                dto.setEos(rs.getFloat("eos")+"");
                dto.setBch(rs.getFloat("bch")+"");
                dto.setQtum(rs.getFloat("qtum")+"");
                dto.setIota(rs.getFloat("iota")+"");
                dto.setLtc(rs.getFloat("ltc")+"");
                dto.setEtc(rs.getFloat("etc")+"");
                dto.setBtg(rs.getFloat("btg")+"");
                dto.setBtc(rs.getFloat("btc")+"");
                dto.setOmg(rs.getFloat("omg")+"");
                dto.setEth(rs.getFloat("eth")+"");
                dto.setXrp(rs.getFloat("xrp")+"");

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
