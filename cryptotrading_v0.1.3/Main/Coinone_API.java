package Main;

import java.io.*;
import java.net.URL;
import java.net.MalformedURLException;
import org.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


public class Coinone_API {

    private static URL url = null;
    private static final String buy_url = "https://api.coinone.co.kr/v2/order/limit_buy/";
    private static final String sell_url = "https://api.coinone.co.kr/v2/order/limit_sell/";
    private static final String balance_url = "https://api.coinone.co.kr/v2/account/balance/";
    private static final String order_url = "https://api.coinone.co.kr/v2/order/limit_orders/";
    private static final String orderbook_url = "https://api.coinone.co.kr/orderbook/?format=json&currency=";
    private static final String ticker_url = "https://api.coinone.co.kr/ticker/?format=json&currency=all";
    private static final String HMAC_SHA512 = "HmacSHA512";

    //ticker (코인의 정보) 받아오는 메소드
    public static JSONObject get_ticker() {

        URL url = null;
        try {
            url = new URL(ticker_url);
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        }

        return get_json(url);
    }

    //orderbook (코인의 현재 주문량) 받아오는 메소드
    public static JSONObject get_orderbook(String coin_name) {
         URL url = null;
         try{
             url = new URL(orderbook_url + coin_name);
         } catch(MalformedURLException e1){
             e1.printStackTrace();
         }

         return get_json(url);
    }

    //get_ticke에서 json을 서버에서 받는 것만 따로 구성하는 get_ticker의 모듈화 메소드
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

        } catch (IOException e) {
            e.printStackTrace();
        }

        return json;
    }

    //specific balance, buy, sell 하는 메소드
    //balance 에 필요한 payload - 빈 JSONObject
    //buy, sell에 필요한 payload - "'price": 살 코인의 가격, "qty": 살 코인의 분량, "currency": 살 코인의 이름
    //order에 필요한 payload - "currency": 산 코인의 이름
    public static JSONObject get_info(JSONObject json_payload, String type, String ACCESS_TOKEN, String SECRET_KEY) throws Exception{

        //논스값 := system class에서 1970년 1월 1일 0시 0분 0초로부터 경과한 시간을 초로
        long nonce = System.currentTimeMillis();

        //URL 객체로부터 balance_url
        HttpPost httpPost;
        if(type.equals("buy")){
            httpPost = new HttpPost(buy_url);

        }else if(type.equals("sell")){
            httpPost = new HttpPost(sell_url);

        }else if(type.equals("balance")){
            httpPost = new HttpPost(balance_url);
        }else{
            httpPost = new HttpPost(order_url);
        }
        //!실제 코드는 여기부터 시작!

        json_payload.put("nonce", nonce);
        json_payload.put("access_token", ACCESS_TOKEN);

        //Base64 인코딩은 org.apache.common.codec 외부 라이브러리 이용
        //split한 이유는 디버깅하면서 알게 됐는데 hex처리 과정에서 \r\n 들어가서
        String payload_str = Base64.encodeBase64String(json_payload.toString().getBytes());

        String[] payload_arr = payload_str.split("\r\n");
        String payload;
        if(type.equals("balance") || type.equals("order")) {
            payload = payload_arr[0] + payload_arr[1];
        }else{
            payload = payload_arr[0] + payload_arr[1] + payload_arr[2];
        }


        //hmac을 이용한 무결성 검사
        String signature = getHmacSha512(SECRET_KEY.toUpperCase(),payload).toLowerCase();

        //http response-request 하는 코드
        //이것 또한 외부 라이브러리 이용, org.apache.http.client
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //coinone api doc에 명시되어있는대로 header 추가
        httpPost.addHeader("Content-Type", "application/json");
        httpPost.addHeader("Accept", "application/json");
        httpPost.addHeader("X-COINONE-PAYLOAD", payload);
        httpPost.addHeader("X-COINONE-SIGNATURE", signature);

        HttpResponse httpResponse = httpClient.execute(httpPost);
        HttpEntity response_entity = httpResponse.getEntity();
        String response_sting = EntityUtils.toString(response_entity);
        //System.out.println(response_sting);

        httpClient.close();

        //반환 값은 디버깅 쉽게 string으로 했는데
        //데이터 처리하려면 json이 쉬울 듯
        JSONObject return_json = new JSONObject(response_sting);
        return return_json;

    }


    //get_info 모듈화 메소드 1
    private static String getHmacSha512(String key, String data) {
        Mac sha512_HMAC;
        String result = null;

        try {
            byte[] byteKey = key.getBytes("UTF-8");
            sha512_HMAC = Mac.getInstance(HMAC_SHA512);
            SecretKeySpec keySpec = new SecretKeySpec(byteKey, HMAC_SHA512);
            sha512_HMAC.init(keySpec);
            byte[] macData = sha512_HMAC.doFinal(data.getBytes("UTF-8"));
            result = bytesToHex(macData);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //get_info 모듈화 메소드 2
    private static String bytesToHex(byte[] bytes) {
        final char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
