package Parse_coin;

import java.io.File;
import java.io.*;
import java.util.Scanner;

public class input_outputmaker {

    private File file;
    private Scanner[] scan = new Scanner[11];
    private BufferedWriter saver;
    private String[] coin = {"bch", "btc", "etc", "eth", "ltc", "xrp"};

    public void sav(){

        int test = 5;
        double[] val = new double[11];
        file = new File(coin[test] + "_difference.txt");
        try {
            saver = new BufferedWriter(new FileWriter(coin[test] + "_test.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            scan[0] = new Scanner(file);
            scan[1] = new Scanner(file);
            scan[2] = new Scanner(file);
            scan[3] = new Scanner(file);
            scan[4] = new Scanner(file);
            scan[5] = new Scanner(file);
            scan[6] = new Scanner(file);
            scan[7] = new Scanner(file);
            scan[8] = new Scanner(file);
            scan[9] = new Scanner(file);
            scan[10] = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        scan[1].nextLine();
        scan[2].nextLine();scan[2].nextLine();
        scan[3].nextLine();scan[3].nextLine();scan[3].nextLine();
        scan[4].nextLine();scan[4].nextLine();scan[4].nextLine();scan[4].nextLine();
        scan[5].nextLine();scan[5].nextLine();scan[5].nextLine();scan[5].nextLine();scan[5].nextLine();
        scan[6].nextLine();scan[6].nextLine();scan[6].nextLine();scan[6].nextLine();scan[6].nextLine();scan[6].nextLine();
        scan[7].nextLine();scan[7].nextLine();scan[7].nextLine();scan[7].nextLine();scan[7].nextLine();scan[7].nextLine();scan[7].nextLine();
        scan[8].nextLine();scan[8].nextLine();scan[8].nextLine();scan[8].nextLine();scan[8].nextLine();scan[8].nextLine();scan[8].nextLine();scan[8].nextLine();
        scan[9].nextLine();scan[9].nextLine();scan[9].nextLine();scan[9].nextLine();scan[9].nextLine();scan[9].nextLine();scan[9].nextLine();scan[9].nextLine();scan[9].nextLine();
        scan[10].nextLine();scan[10].nextLine();scan[10].nextLine();scan[10].nextLine();scan[10].nextLine();scan[10].nextLine();scan[10].nextLine();scan[10].nextLine();scan[10].nextLine();scan[10].nextLine();

        while(true){
            val[0] = Double.parseDouble(scan[0].nextLine());
            val[1] = Double.parseDouble(scan[1].nextLine());
            val[2] = Double.parseDouble(scan[2].nextLine());
            val[3] = Double.parseDouble(scan[3].nextLine());
            val[4] = Double.parseDouble(scan[4].nextLine());
            val[5] = Double.parseDouble(scan[5].nextLine());
            val[6] = Double.parseDouble(scan[6].nextLine());
            val[7] = Double.parseDouble(scan[7].nextLine());
            val[8] = Double.parseDouble(scan[8].nextLine());
            val[9] = Double.parseDouble(scan[9].nextLine());
            val[10] = Double.parseDouble(scan[10].nextLine());

            try {

                if(val[10] == 0.5){

                }else {

                    saver.write(String.valueOf(val[0]));
                    saver.write(",");
                    saver.write(String.valueOf(val[1]));
                    saver.write(",");
                    saver.write(String.valueOf(val[2]));
                    saver.write(",");
                    saver.write(String.valueOf(val[3]));
                    saver.write(",");
                    saver.write(String.valueOf(val[4]));
                    saver.write(",");
                    saver.write(String.valueOf(val[5]));
                    saver.write(",");
                    saver.write(String.valueOf(val[6]));
                    saver.write(",");
                    saver.write(String.valueOf(val[7]));
                    saver.write(",");
                    saver.write(String.valueOf(val[8]));
                    saver.write(",");
                    saver.write(String.valueOf(val[9]));
                    saver.write(",");
                    saver.write(String.valueOf(val[10]));
                    saver.newLine();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public static void main(String[] args){
        input_outputmaker test = new input_outputmaker();
        test.sav();
    }
}
