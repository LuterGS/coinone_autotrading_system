package bean;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

public class SignupDAO {

    private static Connection connection = null;
    private static PreparedStatement statement = null;
    private static ResultSet rs = null;

    private static SignupDAO dao = new SignupDAO();

    //다른 클래스에서 이 클래스의 인스턴스를 new를 통해 생성하지 못하게금
    //싱글톤 패턴 이용
    public static SignupDAO getInstance(){
        return dao;
    }

    private SignupDAO(){}

    //DBCP 과정
    public static Connection getMysql() throws Exception{

        //수정 요함
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/user_database?autoReconnect=true&serverTimezone=UTC";
        Connection conn = DriverManager.getConnection(url,"root","niconiconi990!");

        return conn;

    }

    public ArrayList<SignupDTO> select(){
        ArrayList<SignupDTO> list = new ArrayList<SignupDTO>();
        try{
            connection = getMysql();
            statement = connection.prepareStatement("select * from user_database;");
            rs = statement.executeQuery();

            while(rs.next()){
                SignupDTO dto = new SignupDTO();
                dto.setId(rs.getString("id"));
                //수정요함
                dto.setPassword(rs.getString("pw"));
                //dto.setAccess_token(rs.getString("access_token"));
                //dto.setSecret_key(rs.getString("secret_key"));
                //dto.setEmail(rs.getString("email"));
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

    public void insert (SignupDTO dto){
        try{
            connection = getMysql();
            statement = connection.prepareStatement("insert into user_database (id,password,access_token,secret_key,email) VALUES (?,?,?,?,?);");

            String dto_id = dto.getId();
            String dto_pw = dto.getPassword();
            String dto_at = dto.getAccess_token();
            String dto_sk = dto.getSecret_key();
            String dto_em = dto.getEmail();

            statement.setString(1,dto_id);
            statement.setString(2,dto_pw);
            statement.setString(3,dto_at);
            statement.setString(4,dto_sk);
            statement.setString(5,dto_em);

            statement.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean loginCheck(String id, String pw){
        boolean check = false;

        try{
            connection = getMysql();
            //String sql = "select * from user_database.user_database where id=? and pw=?;";
            String sql = "select * from user_database.user_database where id=? and password=?;";
            statement = connection.prepareStatement(sql);
            statement.setString(1,id);
            statement.setString(2,pw);
            rs = statement.executeQuery();

            if(rs.next()){
                check= true;
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return check;

    }
}
