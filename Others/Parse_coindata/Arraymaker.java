package Parse_coin;

import java.io.*;
import java.util.Scanner;

public class Arraymaker {

    private File file;
    private Scanner[] scan = new Scanner[31];
    private BufferedWriter saver;
    private String[] coin = {"bch", "btc", "etc", "eth", "ltc", "xrp"};


    public void maker(){

        double[] in1 = new double[31];
        double[] in2 = new double[20];
        double[] in3 = new double[20];
        int[] temp = new int[20];
        int result_temp;

        double result_start, result_end, result;

        int te = 5;
        int a, b;
        int minus_count = 0, plus_count = 0, zero_count = 0;

        file = new File(coin[te] + ".txt");
        try {
            for(a = 0; a < 31; a++){
                this.scan[a] = new Scanner(file);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for(a = 0; a < 31; a++){
            for(b = 0; b < a; b++){
                scan[a].nextLine();
            }
        }

        try {
            saver = new BufferedWriter(new FileWriter(coin[te] + "_difference.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }



        while(true){


            for(a = 0; a < 31; a++){
                in1[a] = Double.parseDouble(scan[a].nextLine());
            }

            for(a = 0; a < 20; a++){
                in2[a] = ((in1[a+1] - in1[a]) / in1[a]) * 100 * 10000;
                temp[a] = (int)in2[a];
                in3[a] = ((double)temp[a]) / 10000;
            }
            result_start = in1[21];
            result_end = in1[30];
            result_temp = (int)  (((result_end - result_start) / result_start) * 100 * 10000);
            result = ((double)result_temp) / 10000;

            if(result > 10){
                result = 10;
                plus_count++;
                //System.out.printf("PLUS %d, MINUS %d, ZERO %d\n", plus_count, minus_count, zero_count);
            }else if(result < -10) {
                result = -10;
                minus_count++;
                //System.out.printf("PLUS %d, MINUS %d, ZERO %d\n", plus_count, minus_count, zero_count);
            }else if(result == 0){
                zero_count++;
               // System.out.printf("PLUS %d, MINUS %d, ZERO %d\n", plus_count, minus_count, zero_count);
            }



            try {
                if(result != 0) {
                    for (a = 0; a < 20; a++) {
                        saver.write(String.valueOf(in3[a]));
                        saver.write(",");
                    }
                    saver.write(String.valueOf(result));
                    saver.newLine();
                }
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        Arraymaker test = new Arraymaker();
        test.maker();
    }
}


/*
bch result : plus 252, minus 86, zero 1074
btc result : plus 19, minus 26, zero 1228
etc result : plus 173, minus 106, zero 3812
eth result : plus 94, minus 48, zero 2101
ltc result : plus 158, minus 74, zero 2758
xrp result : plus 480, minus 250, zero 5217

 */
