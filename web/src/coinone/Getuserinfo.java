package coinone;

import com.google.gson.JsonObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
//import org.json.JSONObject;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Getuserinfo {
    public static final String userinfo_url = "https://api.coinone.co.kr/v2/account/user_info/";

    public static String get_info() throws Exception{

        String ACCESS_TOKEN = "225efe50-40a3-4665-8e09-6f95f5df0905";
        String SECRET_KEY = "c8fa018e-b094-4b58-bb0f-47a89fa09c8e";

        long nonce = System.currentTimeMillis();

        JSONObject params = new JSONObject();
        params.put("access_token", ACCESS_TOKEN);
        params.put("nonce", nonce);

        String payload_str = Base64.encodeBase64String(params.toString().getBytes());
        String[] payload_arr = payload_str.split("\r\n");
        String payload = payload_arr[0]+payload_arr[1];

        String signature = Encryptor.getHmacSha512(SECRET_KEY.toUpperCase(),payload).toLowerCase();

        //http response-request 하는 코드
        //이것 또한 외부 라이브러리 이용, org.apache.http.client
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //coinone api doc에 명시되어있는대로 header 추가
        HttpPost httpPost = new HttpPost(userinfo_url);
        httpPost.addHeader("Content-Type", "application/json");
        httpPost.addHeader("Accept", "application/json");
        httpPost.addHeader("X-COINONE-PAYLOAD", payload);
        httpPost.addHeader("X-COINONE-SIGNATURE", signature);

        HttpResponse httpResponse = httpClient.execute(httpPost);
        HttpEntity response_entity = httpResponse.getEntity();



        String response_string = EntityUtils.toString(response_entity, "utf-8");

        //System.out.println(response_string);

        //JSONParser jsonParser = new JSONParser();
        //Object obj = jsonParser.parse(response_string);
        //JSONObject json_return = (JSONObject) obj;

        httpClient.close();

        return response_string;


    }

}
