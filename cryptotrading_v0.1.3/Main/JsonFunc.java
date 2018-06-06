package Main;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonFunc extends DefineData {

    private String avail_coin, balance_coin;
    private double[] balance_coin_toKrwprice;

    public JsonFunc(){

    }

    //에러코드로 제대로된 응답을 받았는지 확인하는 메소드
    public boolean get_errorcode(JSONObject input){

        String check = input.getString("errorCode");

        if(check.equals("0")){
            return true;
        }else{
            return false;
        }
    }


    //JSONObject로 받은 balance를 double 배열로 바꾸는 메소드
    public double[][] get_balance(){

        int a;
        JSONObject balance = this.balance();
        double[][] output = new double[this.key.length][2];

        /*
            Definedata에 있는 String의 key를 따르되, 1번이 Date가 아니라 KRW임
            [][0] : avail - 현재 거래 가능한 액수
           [][1] : balance - 가지고 있는 총 금액
           */
        output[0][0] = MathFunc.double_toDouble_4(Double.parseDouble(((JSONObject) balance.get("krw")).get("avail").toString()));
        output[0][1] = MathFunc.double_toDouble_4(Double.parseDouble(((JSONObject) balance.get("krw")).get("balance").toString()));

        for(a = 1; a < this.key.length; a++){
            output[a][0] = MathFunc.double_toDouble_4(Double.parseDouble(((JSONObject) balance.get(key[a])).get("avail").toString()));
            output[a][1] = MathFunc.double_toDouble_4(Double.parseDouble(((JSONObject) balance.get(key[a])).get("balance").toString()));
        }
        return output;
    }


    //선택한 코인의 매수, 매도가격을 구하는 메소드
    public int[] get_orderbook_minmax(String coin_name){

        int[] output = new int[2];
        //0번째 방은 bid - 매수가격중 가장 높은 가격, 1번째 방은 ask - 매도가격중 가장 낮은 가격
        JSONObject orderbook = Coinone_API.get_orderbook(coin_name);
        JSONArray bid_array = orderbook.getJSONArray("bid");
        JSONArray ask_array = orderbook.getJSONArray("ask");
        //System.out.println(bid_array);
        //System.out.println(ask_array);
        output[0] = Integer.parseInt(bid_array.getJSONObject(0).getString("price"));
        output[1] = Integer.parseInt(ask_array.getJSONObject(0).getString("price"));

        return output;
    }


    //avail중에서 가장 많이 소유중인 코인과, balance 중에서 가장 많이 소유중인 코인을 판별하고 저장하는 메소드
    public void get_highest_availBalance_coin(){

        int a;
        double[][] balance = get_balance();                 //balance를 get_balance 메소드로 가져옴
        int[][] coin_price = new int[11][2];
        double[] avail_coin_krwprice = new double[12];
        double[] balance_coin_krwprice = new double[12];

        //0번째 방에 KRW 값 넣음
        avail_coin_krwprice[0] = balance[0][0];
        balance_coin_krwprice[0] = balance[0][1];

        System.out.printf("avail : %.1f, balance : %.1f\n", avail_coin_krwprice[0], balance_coin_krwprice[0]);

        //코인에 따른 값을 시세를 가져와서 계산해 KRW로 통일해 채워넣음
        for(a = 0; a < 11; a++){
            coin_price[a] = get_orderbook_minmax(key[a+1]);
            avail_coin_krwprice[a+1] = balance[a+1][0] * coin_price[a][1];
            balance_coin_krwprice[a+1] = balance[a+1][1] * coin_price[a][1];

            System.out.printf("avail : %.1f, balance : %.1f\n", avail_coin_krwprice[a+1], balance_coin_krwprice[a+1]);
        }

        //가장 많이 가지고 있는 코인 이름 설정
        this.avail_coin = this.key[MathFunc.get_highest_value(avail_coin_krwprice)];
        this.balance_coin = this.key[MathFunc.get_highest_value(balance_coin_krwprice)];
        if(this.avail_coin.equals("Date")){ this.avail_coin = "krw";}
        if(this.balance_coin.equals("Date")){ this.balance_coin = "krw";}

        avail_coin_krwprice = this.balance_coin_toKrwprice;

        System.out.printf("avail : %s, balance : %s\n", this.avail_coin, this.balance_coin);
    }


    //order가 정상적으로 완료되었는지 확인하는 메소드
    public boolean order_check(String coin_name){

        UserData userData = UserData.getInstance();
        String ACCESS_TOKEN = userData.getCoinone_access_token();
        String SECRET_KEY = userData.getCoinone_secret_key();
        int checker = 0;

        JSONObject input = new JSONObject();
        JSONObject output;
        input.put("currency", coin_name);

        output = get_safe_data(input, "order", ACCESS_TOKEN, SECRET_KEY);
        checker = output.getJSONArray("limitOrders").length();

        if(checker == 0) {
            return true;
        }else{
            return false;
        }

    }


    //balance를 가져오는 메소드, 다른 클래스에서 사용 시에는 double형 2차배열로 넘겨주기 때문에 private임
    private JSONObject balance(){

        UserData userData = UserData.getInstance();
        String ACCESS_TOKEN = userData.getCoinone_access_token();
        String SECRET_KEY = userData.getCoinone_secret_key();
        //System.out.printf("%s, %s\n", ACCESS_TOKEN, SECRET_KEY);

        JSONObject input = new JSONObject();
        JSONObject output = new JSONObject();

        output = get_safe_data(input, "balance", ACCESS_TOKEN, SECRET_KEY);
        return output;
    }


    //sell하는 메소드
    protected JSONObject sell(String coin_name, double quantity, int price){

        boolean successful_checker = false;
        UserData userData = UserData.getInstance();
        String ACCESS_TOKEN = userData.getCoinone_access_token();
        String SECRET_KEY = userData.getCoinone_secret_key();

        JSONObject input = new JSONObject();
        JSONObject output = new JSONObject();
        input.put("currency", coin_name);
        input.put("qty", quantity);
        input.put("price", price);

        output = get_safe_data(input, "sell", ACCESS_TOKEN, SECRET_KEY);
        System.out.println(output);

        return output;
    }


    //buy 하는 메소드
    protected JSONObject buy(String coin_name, double quantity, int price){

        UserData userData = UserData.getInstance();
        String ACCESS_TOKEN = userData.getCoinone_access_token();
        String SECRET_KEY = userData.getCoinone_secret_key();

        JSONObject input = new JSONObject();
        JSONObject output = new JSONObject();
        input.put("currency", coin_name);
        input.put("qty", quantity);
        input.put("price", price);

        output = get_safe_data(input, "buy", ACCESS_TOKEN, SECRET_KEY);
        System.out.println(output);

        return output;
    }


    //정상적인 JSONObject를 받아올 때까지 작업을 반복하는 메소드
    protected JSONObject get_safe_data(JSONObject input, String type, String ACCESS_TOKEN, String SECRET_KEY){

        boolean successful_checker = false;
        JSONObject output = new JSONObject();

        while(true) {
            try {
                output = Coinone_API.get_info(input, type, ACCESS_TOKEN, SECRET_KEY);
                successful_checker = true;
            } catch (Exception e) {
                e.printStackTrace();
                successful_checker = false;
            }

            if(successful_checker){
                break;
            }
        }
        return output;
    }


    public String getAvail_coin() {
        return avail_coin;
    }

    public String getBalance_coin(){
        return balance_coin;
    }

    public double[] getBalance_coin_toKrwprice() {
        return balance_coin_toKrwprice;
    }
}
