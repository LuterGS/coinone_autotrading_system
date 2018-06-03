package Main;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class parsingJson {
    
        public double[][] parsingCoinData(){

            double[][]  coin = new double[12][2];
            int [][] coinTemp = new int[12][2];
           /*
           [0][]: ltc
           [1][]: bch
           [2][]: eos
           [3][]: qtum
           [4][]: krw
           [5][]: iota
           [6][]: etc
           [7][]: btg
           [8][]: btc
           [9][]: omg
           [10][]: eth
           [11][]: xrp

           [][0] : avail
           [][1] : balance
           */

            try {
                JSONParser jsonParser = new JSONParser();
               
                Object obj = jsonParser.parse(new FileReader("C:\\Users\\이호현\\test.json"));
                JSONObject jsonObj = (JSONObject) obj;

                coin[0][0] = Double.parseDouble(((JSONObject) jsonObj.get("ltc")).get("avail").toString());
                coin[0][1] = Double.parseDouble(((JSONObject) jsonObj.get("ltc")).get("balance").toString());
                coin[1][0] = Double.parseDouble(((JSONObject) jsonObj.get("bch")).get("avail").toString());
                coin[1][1] = Double.parseDouble(((JSONObject) jsonObj.get("bch")).get("balance").toString());
                coin[2][0] = Double.parseDouble(((JSONObject) jsonObj.get("eos")).get("avail").toString());
                coin[2][1] = Double.parseDouble(((JSONObject) jsonObj.get("eos")).get("balance").toString());
                coin[3][0] = Double.parseDouble(((JSONObject) jsonObj.get("qtum")).get("avail").toString());
                coin[3][1] = Double.parseDouble(((JSONObject) jsonObj.get("qtum")).get("balance").toString());
                coin[4][0] = Double.parseDouble(((JSONObject) jsonObj.get("krw")).get("avail").toString());
                coin[4][1] = Double.parseDouble(((JSONObject) jsonObj.get("krw")).get("balance").toString());
                coin[5][0] = Double.parseDouble(((JSONObject) jsonObj.get("iota")).get("avail").toString());
                coin[5][1] = Double.parseDouble(((JSONObject) jsonObj.get("iota")).get("balance").toString());
                coin[6][0] = Double.parseDouble(((JSONObject) jsonObj.get("etc")).get("avail").toString());
                coin[6][1] = Double.parseDouble(((JSONObject) jsonObj.get("etc")).get("balance").toString());
                coin[7][0] = Double.parseDouble(((JSONObject) jsonObj.get("btg")).get("avail").toString());
                coin[7][1] = Double.parseDouble(((JSONObject) jsonObj.get("btg")).get("balance").toString());
                coin[8][0] = Double.parseDouble(((JSONObject) jsonObj.get("btc")).get("avail").toString());
                coin[8][1] = Double.parseDouble(((JSONObject) jsonObj.get("btc")).get("balance").toString());
                coin[9][0] = Double.parseDouble(((JSONObject) jsonObj.get("omg")).get("avail").toString());
                coin[9][1] = Double.parseDouble(((JSONObject) jsonObj.get("omg")).get("balance").toString());
                coin[10][0] = Double.parseDouble(((JSONObject) jsonObj.get("eth")).get("avail").toString());
                coin[10][1] = Double.parseDouble(((JSONObject) jsonObj.get("eth")).get("balance").toString());
                coin[11][0] = Double.parseDouble(((JSONObject) jsonObj.get("xrp")).get("avail").toString());
                coin[11][1] = Double.parseDouble(((JSONObject) jsonObj.get("xrp")).get("balance").toString());

               //System.out.println(((JSONObject) jsonObj.get("xrp")).get("avail").toString());
                for(int i =0; i<12; i++){
                    for(int j=0; j<2; j++){
                        coinTemp[i][j] = (int) (1000* coin[i][j]);
                        coin[i][j] = (double) (coinTemp[i][j]);
                        coin[i][j] = coin[i][j]/1000;
                    }
                }


            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return coin;
        }

}
