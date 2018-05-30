package Main;

import java.sql.*;

public class MySQL {

    private Statement statement;
    private Userdata user = new Userdata();
    private Connection connection;
    private ResultSet resultset;

    public MySQL(){

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(user.getMySQL_Address(), user.getMySQL_username(), user.getMySQL_password());
            this.statement = connection.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //TEST
    public void insert_data(String table_name, String[] key, String[] value){

        String input;
        int a;

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

        System.out.println(input);

        try {
            statement.executeUpdate(input);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ResultSet select_data(String table_name, String[] key, String order_key, String order_type){

        String input;
        int a;

        input = "select ";
        for(a = 0; a < key.length - 1; a++){
            input += key[a] + ",";
        }
        input = input.substring(0, input.length() - 1);
        input += " from " + table_name + " order by " + order_key + " " + order_type;

        System.out.println(input);

        try {
            this.resultset = statement.executeQuery(input);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this.resultset;
    }

    public ResultSet get_latest_traindata(int num){

        String input = "select * from coin where date > " + String.valueOf(num - 21) + " order by date desc";

        try{
            this.resultset = statement.executeQuery(input);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return this.resultset;

    }

    public void insert_resultData(String coinname, double percent){

        int temp = user.get_latestnum("./RESULT_NUM.txt");
        String input = "insert into result (Date, coin, percent) values" +
                "(" + String.valueOf(temp) + "," + coinname + "," + String.valueOf(percent) + ");";
        user.set_latestnum(temp, "./RESULT_NUM.txt");

        try {
            statement.executeQuery(input);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
    

