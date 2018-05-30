package Main;

import java.io.*;
import java.util.Scanner;


public class Userdata {
//UserData를 가져와서 보관하는 객체. 나중에 Coinone API 관련 UserData나, Email등도 보관할 것임

    private Scanner file;
    private String MySQL_Address;
    private String MySQL_username;
    private String MySQL_password;


    public Userdata() {

        try {
            this.file = new Scanner(new File("UserData.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.MySQL_Address = file.nextLine();
        this.MySQL_username = file.nextLine();
        this.MySQL_password = file.nextLine();
        /*this.MySQL_Address = this.MySQL_Address.substring(22, this.MySQL_Address.length() - 1);
        this.MySQL_username = this.MySQL_username.substring(16, this.MySQL_Address.length() - 1);
        this.MySQL_password = this.MySQL_password.substring(16, this.MySQL_Address.length() - 1);*/
        System.out.println(this.MySQL_Address);
        System.out.println(this.MySQL_username);
        System.out.println(this.MySQL_password);

        this.MySQL_Address += "?autoReconnect=true&useSSL=false&serverTimezone=UTC";

        System.out.println(this.MySQL_Address);
    }

    public int get_latestnum(String filepath){

        File numfile = new File(filepath);
        Scanner scan;
        int num = 1;
        String num_string;
        try {
            scan = new Scanner(numfile);
            num_string = scan.nextLine();
            num = Integer.parseInt(num_string);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return num;
    }

    public void set_latestnum(int num, String filepath){

        File numfile = new File(filepath);
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(numfile));
            writer.write(String.valueOf(num + 1));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getMySQL_Address(){
        return this.MySQL_Address;
    }

    public String getMySQL_username(){
        return this.MySQL_username;
    }

    public String getMySQL_password(){
        return this.MySQL_password;
    }
}
