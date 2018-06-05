package Main;

import java.io.*;
import java.util.Scanner;


//파일을 쉽게 관리할 수 있는 메소드
public class FileFunc {


    //boolean 역할을 하는 파일의 번호나, int형 하나만 써져있는 파일 번호를 바꾸는 메소드
    public static void set_num(String filepath, int num){

        File file = new File(filepath);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(String.valueOf(num));
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //파일을 새로 만들어서 저장하는 메소드, contents를 입력받아 저장함
    public static void set_file(String filepath, String[] contents){

        File file = new File(filepath);
        int a;

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for(a = 0; a < contents.length; a++){
                writer.write(contents[a]);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //파일의 값을 읽어들여오는 메소드
    public static String[] read_from_file(String filepath, String type){

        String[] output = {"null"};

        if(type.equals("num_1")){
            output = scan_file(filepath, 1);
        }else if(type.equals("ai_return")){
            output = scan_file(filepath, 11);
        }

        return output;
    }


    //read_from_file의 하위 메소드, num 만큼의 line을 입력받아 저장 후 return
    private static String[] scan_file(String filepath, int num){

        String[] output = new String[num];
        File file = new File(filepath);
        int a;

        try {
            Scanner scan = new Scanner(file);
            for(a = 0; a < num; a++){
                output[a] = scan.nextLine();
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return output;
    }
}
