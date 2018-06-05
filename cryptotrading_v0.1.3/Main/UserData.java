package Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//싱글톤으로 구현, 파일로 저장된 mysql 로그인 정보, coinone key를 읽고 리턴하는 클ㄹ스
public class UserData {

    private File mysql_info, coinone_info;

    private String mysql_url, mysql_username, mysql_password;
    private String coinone_access_token, coinone_secret_key;
    private Scanner scan;
    private static UserData user_data;

    private UserData(){

        read_file();
    }

    //싱글톤 구성
    public static UserData getInstance(){

        if(user_data == null){
            user_data = new UserData();
        }

        return user_data;
    }

    //클래스 메소드 File 2개를 각각 지정함 (파일을 읽어들여옴)
    private void read_file(){

        this.mysql_info = new File("./UserData/mysql_info.txt");
        this.coinone_info = new File("./UserData/coinone_info.txt");
    }

    //mysql의 url, username, password를 읽어들여오는 메소드
    private void set_mysql_info(){

        try {

            this.scan = new Scanner(this.mysql_info);
            this.mysql_url = scan.nextLine();
            this.mysql_username = scan.nextLine();
            this.mysql_password = scan.nextLine();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //coinone의 key들을 읽어들여오는 메소드
    private void set_coinone_info(){

        try {

            this.scan = new Scanner(this.coinone_info);
            this.coinone_access_token = scan.nextLine();
            this.coinone_secret_key = scan.nextLine();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    //나머지 5개 메소드는 정보들을 리턴하는 getter

    public String getMysql_url(){

        set_mysql_info();
        return this.mysql_url + "?autoReconnect=true&useSSL=false&serverTimezone=UTC";
    }

    public String getMysql_username(){

        set_mysql_info();
        return this.mysql_username;
    }

    public String getMysql_password(){

        set_mysql_info();
        return this.mysql_password;
    }

    public String getCoinone_access_token(){

        set_coinone_info();
        return this.coinone_access_token;
    }

    public String getCoinone_secret_key(){

        set_coinone_info();
        return this.coinone_secret_key;
    }


}
