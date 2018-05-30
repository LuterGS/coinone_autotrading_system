package Main;

import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.net.MalformedURLException;

public class Coinone_API {
//Static으로 선언, 함수처럼 가져다 쓸 수 있게 구성

    private static URL url = null;
    private static final String buy_url = "https://api.coinone.co.kr/v2/order/limit_buy/";
    private static final String sell_url = "https://api.coinone.co.kr/v2/order/limit_sell/";
    private static final String balance_url = "https://api.coinone.co.kr/v2/account/balance/";
    private static final String order_url = "https://api.coinone.co.kr/v2/order/limit_orders/";
    private static final String orderbook_url = "https://api.coinone.co.kr/orderbook/?format=json&currency=";
    private static final String ticker_url = "https://api.coinone.co.kr/ticker/?format=json&currency=all";

    public static JSONObject get_ticker() {

        URL url = null;
        try {
            url = new URL(ticker_url);
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        }

        return get_json(url);
    }

    private static JSONObject get_json(URL url){

        InputStream read_url = null;
        StringBuilder temp_jsonString = new StringBuilder();
        String jsonString = "";
        JSONObject json = null;

        int temp = 0;

        try {
            read_url = url.openStream();

            while(true){
                if(temp == -1){
                    break;
                }
                temp = read_url.read();
                temp_jsonString.append((char) temp);
            }

            jsonString = temp_jsonString.toString();
            jsonString = jsonString.substring(0, jsonString.length() - 1);
            json = new JSONObject(jsonString);

            /*String test = (String) json.get("timestamp");
            JSONObject test2 = (JSONObject) json.get("btc");
            String test3 = (String) test2.get("last");
            System.out.println(test + test3);*/



            jsonString = json.toString();
            System.out.println(jsonString);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return json;
    }
}


