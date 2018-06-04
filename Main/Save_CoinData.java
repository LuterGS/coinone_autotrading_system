package Main;

import org.json.JSONObject;

public class Save_CoinData extends DefineData implements Runnable{

    private JSONObject ticker;
    private String[] value;
    private MysqlFunc mysqlFunc;

    //처음 실행될 때, mysql 데이터베이스에 로그인하는 부분
    public Save_CoinData(){

        //userdata를 가져와 정보를 입력받음
        UserData userData = UserData.getInstance();
        String mysql_url = userData.getMysql_url();
        String mysql_username = userData.getMysql_username();
        String mysql_password = userData.getMysql_password();

        //mysql 로그인
        mysqlFunc = new MysqlFunc(mysql_url, mysql_username, mysql_password);
    }

    //계속 구동되는 함수
    public void run() {

        while(true){
            ticker = Coinone_API.get_ticker();
            set_data();
            mysqlFunc.insert_data("coin", key, value);

            try {
                Thread.sleep(300000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //ticker에서 값을 읽어들여와 MySQL에 저장하는 메소드
    public void set_data(){

        String table_name = "coin";
        value = new String[key.length];
        JSONObject coin_data = null;

        //최신 Date를 LATEST_DATA.txt에서 가져옴
        int Datenum = Integer.parseInt(FileFunc.read_from_file("./LATEST_DATA.txt", "num_1")[0]);
        int a;

        //ticker에서 읽어들여온 값들을 파싱, 다시 값 저장할 때 1 더해줌
        FileFunc.set_num("./LATEST_DATA.txt", Datenum + 1);
        System.out.println("Set Datenum complete");

        value[0] = String.valueOf(Datenum);
        for (a = 1; a < key.length; a++) {
            coin_data = get_coininfo(key[a]);
            value[a] = coin_data.getString("last");
        }
    }

    //set_data의 모듈화 메소드, 하위 JSON들을 파싱하는 메소드
    private JSONObject get_coininfo(String coin_name){

        JSONObject coin = null;
        return coin = (JSONObject) this.ticker.get(coin_name);
    }
}
