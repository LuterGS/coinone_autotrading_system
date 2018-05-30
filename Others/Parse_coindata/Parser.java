package Parse_coin;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Scanner;

public class Parser {

    private Scanner scan;
    private File input_json;
    private JSONObject parser = new JSONObject();
    private JSONArray arra;
    private BufferedWriter write;

    private void choose_coin(int type) throws FileNotFoundException {
        JSONParser par =  new JSONParser();
        int a;



        if(type == 1){
            try {
                write = new BufferedWriter(new FileWriter("xrp.txt"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            input_json = new File("C:\\Users\\LuterGS\\OneDrive - konkuk.ac.kr\\Development\\Java\\코인 데이터\\xrp.json");
            scan = new Scanner(input_json);

            try {
                arra = (JSONArray) par.parse(scan.nextLine());
                System.out.println("Sizeof array : " + arra.size());

                for(a = 0; a < arra.size(); a++) {
                    parser = (JSONObject) arra.get(a);
                    write.write(String.valueOf(String.valueOf(parser.get("close"))));
                    write.newLine();
                    //write.write(parser.get("close"));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void parse(){

        int a = 0;
        JSONObject data;


        System.out.println(parser.get("date"));
        System.out.println(parser.get("high"));
        System.out.println(parser.get("low"));
        System.out.println(parser.get("open"));
        System.out.println(parser.get("close"));

    }

    public void par(){

        try {
            choose_coin(1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        parse();
    }


    public static void main(String[] args){

        Parser parse = new Parser();
        parse.par();
    }

}
