package coinone;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;


public class Getbalance {
    private static final String balance_url = "https://api.coinone.co.kr/v2/account/balance/";
    private static final String HMAC_SHA512_ALGORITHM = "HmacSHA512";

    public static String get_balance(){

        //논스값 := system class에서 1970년 1월 1일 0시 0분 0초로부터 경과한 시간을 초로
        long nonce = System.currentTimeMillis();

        //URL 객체로부터 balance_url
        try {
            URL url = new URL(balance_url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        //ACCESS_TOKEN이랑 SECRETE_KEY 선언
        //final로 빼도 될 듯
        String ACCESS_TOKEN = "225efe50-40a3-4665-8e09-6f95f5df0905";
        String SECRET_KEY = "c8fa018e-b094-4b58-bb0f-47a89fa09c8e";

        //아래 주석처리 한 부분은 payload json 처리 후 base64 인코딩 하는 코드
        //python에서 base64 인코딩 한 byte hex값과 일치!
        //split 한 이유는 인코딩 과정에서 json 콜론 사이의 띄어쓰기 맞춰 주려고
        //작동 잘 되지만 모듈화 과정에서 버린 코드

        /*
        JSONObject payload = new JSONObject();
        payload.put("access_token", ACCESS_TOKEN);
        payload.put("nonce", Long.toString(nonce));

        String[] str_payload1 = payload.toString().split(":");
        String str_1 = str_payload1[0]+": "+str_payload1[1]+": "+str_payload1[2];
        //System.out.println(str_1);
        String[] str_payload2 = str_1.split(",");
        String str_2 = str_payload2[0]+", "+str_payload2[1];
        //System.out.println(str_2);

        //String str_payload = payload.toString();

        //byte array로 payload 인코딩 완료
        //byte[] encoded_payload = Base64.getEncoder().encode(temp_payload);

        byte[] temp_payload = str_to_byte(str_2);

         */


        //hmac_sha512를 이용한 무결성 검사 및 hex값 변환
        //Encrypt class로 따로 빼놓음
        //마찬가지로 작동 잘 되지만 모듈화 과정에서 버린 코드

        /*

        Mac signature = null;

        signature = Mac.getInstance(HMAC_SHA512_ALGORITHM);
        SecretKeySpec signingKey = new SecretKeySpec(str_to_byte(SECRET_KEY.toUpperCase()), HMAC_SHA512_ALGORITHM);
        signature.init(signingKey);

        byte[] temp_signature = signature.doFinal(encoded_payload);

        String hex_signature = new String(Hex.encodeHex(temp_signature));
        System.out.println(hex_signature);

        */

        //실제 코드는 여기부터 시작

        //우선 java에는 object type이 없으므로 json 형식으로 payload 작성
        JSONObject params = new JSONObject();
        params.put("nonce", nonce);
        params.put("access_token", ACCESS_TOKEN);

        //Base64 인코딩은 org.apache.common.codec 외부 라이브러리 이용
        //split한 이유는 디버깅하면서 알게 됐는데 인코딩 과정에서 \r\n 들어감
        //이거 해결하니까 통신 에러 사라짐...
        String payload_str = Base64.encodeBase64String(params.toString().getBytes());
        String[] payload_arr = payload_str.split("\r\n");
        String payload = payload_arr[0]+payload_arr[1];



        //hmac을 이용한 무결성 검사
        String signature = Encryptor.getHmacSha512(SECRET_KEY.toUpperCase(),payload).toLowerCase();

        //http response-request 하는 코드
        //이것 또한 외부 라이브러리 이용, org.apache.http.client
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //coinone api doc에 명시되어있는대로 header 추가
        HttpPost httpPost = new HttpPost(balance_url);
        httpPost.addHeader("Content-Type", "application/json");
        httpPost.addHeader("Accept", "application/json");
        httpPost.addHeader("X-COINONE-PAYLOAD", payload);
        httpPost.addHeader("X-COINONE-SIGNATURE", signature);

        HttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpEntity response_entity = httpResponse.getEntity();
        String response_sting = null;
        try {
            response_sting = EntityUtils.toString(response_entity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(response_sting);

        try {
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response_sting;

    }
}
