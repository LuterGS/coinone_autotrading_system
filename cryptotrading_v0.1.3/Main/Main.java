package Main;

import org.json.JSONObject;

public class Main {
    
    public static void main(String[] args){


        Save_CoinData save_coinData = new Save_CoinData();
        Buy_Sell buy_sell = new Buy_Sell();

        Thread thread1 = new Thread(save_coinData);
        Thread thread2 = new Thread(buy_sell);

        thread1.start();
        thread2.start();



        /*
        JSONObject test = new JSONObject();
        JSONObject result;
        test.put("currency", "btc");
        UserData userData = UserData.getInstance();
        String Access = userData.getCoinone_access_token();
        String Secret = userData.getCoinone_secret_key();

        try {
            result = Coinone_API.get_info(test, "order", Access, Secret);
            System.out.println(result);
            int ordernum = result.getJSONArray("limitOrders").length();
            System.out.println(ordernum);
        } catch (Exception e) {
            e.printStackTrace();
        }
*/

        //EmailFunc.sendMail("BTC", "10.3", "120329", "2042", 1);
    }
}
