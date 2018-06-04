package Main;

import java.sql.*;

public class MysqlFunc {

    private Statement statement;
    private Connection connection;
    private ResultSet resultset;


    //생성자, url, username, password를 입력받아 mysql에 로그인하는 부분
    public MysqlFunc(String url, String username, String password){

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(url, username, password);
            this.statement = connection.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //테이블 이름, key값, key에 해당하는 value값을 입력받아 저장하는 메소드
    public void insert_data(String table_name, String[] key, String[] value){

        int a, b;
        String input;

        input = "insert" + " into " + table_name + "(";
        for(a = 0; a < key.length; a++){
            input += key[a] + ",";
        }
        input = input.substring(0, input.length() - 1);
        input += ") values(";
        for(a = 0; a < value.length; a++){
            input += value[a] + ",";
        }
        input = input.substring(0, input.length() - 1);
        input += ")";

        try {
            statement.executeUpdate(input);
            System.out.printf("%s complete\n", input);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //입력받은 값으로부터 20개 이전까지의의 데이터를 ResultSet으로 리턴해주는 메소드
    public ResultSet get_latest_traindata(String table_name, int num){

        String input = "select * from " + table_name + " where date > " + String.valueOf(num - 22) + " order by date desc";

        try{
            this.resultset = statement.executeQuery(input);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return this.resultset;

    }
}
