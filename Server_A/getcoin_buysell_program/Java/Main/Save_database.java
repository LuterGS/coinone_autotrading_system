package Main;

import org.json.JSONObject;


public class Save_database implements Runnable {

    private JSONObject json = null;
    private MySQL mysql = new MySQL();
    private Userdata userdata = new Userdata();

    private void get_json(){

        this.json = Coinone_API.get_ticker();
    }

    private JSONObject get_coininfo(String coin_name){

        JSONObject coin = null;
        return coin = (JSONObject) this.json.get(coin_name);
    }


    public void run() {

        int a;

        String table_name = "coin";
        String[] key_name = {"Date","eos", "bch", "qtum", "iota", "ltc", "etc", "btg", "btc", "omg", "eth","xrp"};
        String[] key_value = new String[key_name.length];
        JSONObject coin_data = null;
        int Datenum = 0;

        while(true) {

            get_json();
            Datenum = userdata.get_latestnum("./LATEST_DATA.txt");
            userdata.set_latestnum(Datenum, "./LATEST_DATA.txt");
            key_value[0] = String.valueOf(Datenum);
            for (a = 1; a < key_name.length; a++) {
                coin_data = get_coininfo(key_name[a]);
                key_value[a] = coin_data.getString("last");
            }

            mysql.insert_data(table_name, key_name, key_value);

            //System.out.println("START select_data");

            try {
                Thread.sleep(300000);
                //1분에 한번씩 받아옴
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
