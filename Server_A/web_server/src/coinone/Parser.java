package coinone;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.lang.*;
import java.util.HashMap;
import java.util.Map;

public class Parser {

    public static String[][] balanceParse(String input){
        Map<String,Object> map = null;
        String[][] balance = new String[12][2];

        try {
            map = new ObjectMapper().readValue(input, HashMap.class);
            //System.out.println(map);
            //System.out.println(map.get("ltc"));

            String ltc = map.get("ltc").toString();
            balance[0][0] = ltc.substring(7,17);
            balance[0][1] = ltc.substring(27,37);

            String bch = map.get("bch").toString();
            balance[1][0] = bch.substring(7,17);
            balance[1][1] = bch.substring(27,37);

            String eos = map.get("eos").toString();
            balance[2][0] = eos.substring(7,17);
            balance[2][1] = eos.substring(27,37);

            String qtum = map.get("qtum").toString();
            balance[3][0] = qtum.substring(7,17);
            balance[3][1] = qtum.substring(27,37);

            String krw = map.get("krw").toString();
            String[] temp = krw.split(",");
            balance[4][0] = temp[0].split("=")[1];
            balance[4][1] = temp[1].split("=")[1].substring(0,temp[1].split("=")[1].length()-1);

            String iota = map.get("iota").toString();
            balance[5][0] = iota.substring(7,17);
            balance[5][1] = iota.substring(27,37);

            String etc = map.get("etc").toString();
            balance[6][0] = etc.substring(7,17);
            balance[6][1] = etc.substring(27,37);

            String btg = map.get("btg").toString();
            balance[7][0] = btg.substring(7,17);
            balance[7][1] = btg.substring(27,37);

            String btc = map.get("btc").toString();
            balance[8][0] = btc.substring(7,17);
            balance[8][1] = btc.substring(27,37);

            String omg = map.get("omg").toString();
            balance[9][0] = omg.substring(7,17);
            balance[9][1] = omg.substring(27,37);

            String eth = map.get("eth").toString();
            balance[10][0] = eth.substring(7,17);
            balance[10][1] = eth.substring(27,37);

            String xrp = map.get("xrp").toString();
            balance[11][0] = xrp.substring(7,17);
            balance[11][1] = xrp.substring(27,37);

        } catch (IOException e) {
            e.printStackTrace();
        }



        /*


        ObjectMapper mapper = new ObjectMapper();
        Map<Object,Object> map = new HashMap<Object,Object>();

        try {
            map = mapper.readValue(input, new TypeReference<Map<String,String>>(){});
            System.out.println(map);
            System.out.println(map.get("ltc"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        */

        /*
        JSONParser parser = new JSONParser();
        try {
            JSONObject json = (JSONObject)parser.parse(input);
            String ltc = (String)json.get("ltc");
            System.out.println(ltc);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        */

        return balance;
    }

    public static String[] usrinfoParse(String input){
        Map<String,Object> map = null;

        String[] usrinfo = new String[6];

        try {
            map = new ObjectMapper().readValue(input, HashMap.class);

            String userInfo = map.get("userInfo").toString();

            //security
            usrinfo[0] =userInfo.split(",")[0].split("=")[1];

            //이름
            usrinfo[1] = userInfo.split(",")[1].split("=")[2];
            //bankcode
            usrinfo[2] = userInfo.split(",")[2].split("=")[1];
            //account
            usrinfo[3] = userInfo.split(",")[4].split("=")[1].substring(0,userInfo.split(",")[4].split("=")[1].length()-1);
            //email
            usrinfo[4] = userInfo.split(",")[5].split("=")[2];

            //phonenum
            usrinfo[5] = userInfo.split(",")[33].split("=")[1];

            for(int i = 0 ; i < 6 ; i++){
                System.out.println(usrinfo[i]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return usrinfo;
    }
}
