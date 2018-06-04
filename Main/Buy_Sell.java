package Main;

import org.json.JSONObject;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Buy_Sell extends DefineData implements Runnable {

    private MysqlFunc mysqlFunc;
    private JsonFunc jsonFunc = new JsonFunc();

    private ResultSet traindata_from_mysql;
    private int latest_ticker_date;
    private String highest_coin_name;
    private double highest_coin_percent;
    private double[][] balance;

    private boolean tradeSwitch = false; // false 면 Buy,true 면 Sell
    private String myCoinName;                                          //구매한 코인 이름
    private Double myCoinAmount;                                        //구매한 코인의 양
    private int up_coinValue, down_coinValue, buy_coinValue;      //예상 코인의 상승MAX, 하강MAX, 구매당시 가격
    private int cur_coinValue;                                        //현재 코인 가격 (Sell 기준)

    //생성자로 mysql에 로그인, 방식은 userdata의 instance를 가져와 로그인
    public Buy_Sell(){

        UserData userData = UserData.getInstance();
        String url = userData.getMysql_url();
        String username = userData.getMysql_username();
        String password = userData.getMysql_password();
        mysqlFunc = new MysqlFunc(url, username, password);


    }


    public void run() {

        set_training_data();
        start_training();
        this.myCoinName = highest_coin_name;


        while(true){

            this.cur_coinValue = jsonFunc.get_orderbook_minmax(myCoinName)[0];
            balance = jsonFunc.get_balance();

            //코인 구매
            if(!tradeSwitch){

                //모든 값이 음수가 아닐 때
                if(!dropCheck()){

                    this.coinBuy();
                    tradeSwitch = true;
                }
            }


            //상승 코인 판매
            if(checkChange()){

                this.coinSell();
                tradeSwitch = false;
                set_training_data();
                start_training();
            }


            //떡락 코인 판매
            if(tradeSwitch){

                if(cur_coinValue < down_coinValue){

                    this.coinSell();
                    tradeSwitch = false;
                    set_training_data();
                    start_training();
                }
            }

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    private void coinBuy() {

        this.myCoinName = this.highest_coin_name;

        while(true) {

            this.buy_coinValue = jsonFunc.get_orderbook_minmax(highest_coin_name)[1];
            this.up_coinValue = (int) (this.buy_coinValue * (1 + highest_coin_percent));
            this.down_coinValue = (int) (this.buy_coinValue * (1 - highest_coin_percent));

            this.myCoinAmount = MathFunc.double_toDouble_4((balance[0][0] / this.buy_coinValue) * 0.99);

            JSONObject result = jsonFunc.buy(highest_coin_name, this.myCoinAmount, this.buy_coinValue);
            //System.out.println(result);

            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(jsonFunc.get_errorcode(result)){
                System.out.printf("Buy Complete! buy %s, qty %.1f, price %d\n", highest_coin_name, this.myCoinAmount, this.buy_coinValue);
                break;
            }
        }

        //서버정상작동 확인
    }


    //기존 예상치보다 조금이라도 더 많이 떡상했으면 즉시 판매
    private void coinSell() {

        String[] mysql_key = new String[5], mysql_value = new String[5];

        while(true) {



            JSONObject result = jsonFunc.sell(this.myCoinName, this.myCoinAmount * 0.99, this.cur_coinValue);
            //System.out.println(result);

            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(jsonFunc.get_errorcode(result)){

                mysql_key[0] = "Date"; mysql_key[1] = "coin"; mysql_key[2] = "buy_valeue"; mysql_key[3] = "sell_value"; mysql_key[5] = "profit";
                mysql_value[0] = String.valueOf(latest_ticker_date);
                mysql_value[1] = myCoinName;
                mysql_value[2] = String.valueOf(buy_coinValue);
                mysql_value[3] = String.valueOf(cur_coinValue);
                mysql_value[4] = String.valueOf(cur_coinValue - buy_coinValue);
                this.myCoinName = "null";
                this.myCoinAmount = 0.0;
                this.buy_coinValue = 0;
                this.up_coinValue = 0;
                this.down_coinValue = 0;

                mysqlFunc.insert_data("buysell", mysql_key, mysql_value);
                System.out.printf("Sell Complete! Sell %s, profit is %s\n", mysql_value[2], mysql_value[4]);
                break;
            }
        }

    }

    //기존 예상치보다 떡상인지 체크
    private boolean checkChange() {

        if (cur_coinValue > up_coinValue) {return true;}

        return false;

    }

    //실시간 최고 코인이 떡락중인지 체크
    private boolean dropCheck() {

        if (highest_coin_percent < 0) { return true; }
        else { return false; }
    }






    //인공신경망에 학습시킬 데이터셋을 파일로 저장하는 부분
    private void set_training_data(){

        //메소드 내의 지역 변수 초기화 부분. latest_ticker_date로 latest data를 받아옴
        int a = 0, b = 0;
        this.latest_ticker_date = Integer.parseInt(FileFunc.read_from_file("./LATEST_DATA.txt", "num_1")[0]);
        double[][] train_data_before = new double[11][21];
        double[][] train_data_after = new double[11][20];
        String[] file_contents = {"0","0","0","0","0","0","0","0","0","0","0"};

        //mysql에서 정보를 읽어옴, resultset으로 저장
        traindata_from_mysql = mysqlFunc.get_latest_traindata("coin", latest_ticker_date);

        //mysql에서 읽어들여온 정보를 분석해 train_data_before, after에 각각 넣음.
        //before는 코인값이 그대로 들어가있는 RAW DATA, after는 증감값을 나타내는 percent값들.
        try {
            while(traindata_from_mysql.next()){

                for(a = 0; a < this.key.length - 1; a++){
                    train_data_before[a][b] = Double.parseDouble(traindata_from_mysql.getString(this.key[a + 1]));
                    //System.out.printf("cur data : coin %s, %.1f, [%d][%d]\n", this.key[a+1], train_data_before[a][b], a, b);
                }

                if(b == 20){
                    break;
                }
                b++;
            }

            for(a = 0; a < 11; a++){
                for(b = 0; b < 20; b++){
                    train_data_after[a][b] =
                            MathFunc.double_toDouble_4((train_data_before[a][b+1] - train_data_before[a][b])/train_data_before[a][b] * 100);
                    //System.out.printf("IN : %.1f, %.1f , %.1f\n", train_data_after[a][b], train_data_before[a][b], train_data_before[a][b+1]);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        //한 코인에 대한 데이터 당 String 하나에 저장
        for(a = 0; a < 11; a++){
            for(b = 0; b < 20; b++){
                file_contents[a] += String.valueOf(train_data_after[a][b]);
                file_contents[a] += ",";
            }
        }
        for(a = 0; a < 11; a++){
            file_contents[a] = file_contents[a].substring(1, file_contents[a].length());
            //System.out.println(file_contents[a]);
        }

        //Training_Data에 저장
        FileFunc.set_file("./Training_Data.txt", file_contents);
    }


    //트레이닝 시작 후 결과값 저장하는 메소드
    private void start_training(){

        int a, highest = 0;
        String[] train_result_string, mysql_input = new String[14], mysql_key_input = new String[14];
        double[] train_result_percent = new double[11];

        //파이썬 실행, 파이썬이 완료될 때까지 기다림
        try {
            Runtime.getRuntime().exec("python3 coin_cnn.py");
            while(true){

                if(Integer.parseInt(FileFunc.read_from_file("./Train_avaliable.txt", "num_1")[0]) == 1){

                    FileFunc.set_num("./Train_avaliable.txt", 0);
                    break;
                }

                Thread.sleep(10000);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //트레이닝 데이터를 읽어들여옴
        train_result_string = FileFunc.read_from_file("./Train_result.txt", "ai_return");
        mysql_input[0] = String.valueOf(this.latest_ticker_date);
        mysql_key_input[0] = "Date";

        for(a = 0; a < 11; a++){
            train_result_percent[a] = (Double.parseDouble(train_result_string[a].split(",")[1]) / 100)- 10.01;
            mysql_input[a+3] = String.valueOf(train_result_percent[a]);
            mysql_key_input[a+3] = this.key[a+1];
        }

        //가장 높은 값 찾고, 트레이닝 값 결과에 저장
        highest = MathFunc.get_highest_value(train_result_percent);
        this.highest_coin_name = this.key[highest + 1];
        this.highest_coin_percent = train_result_percent[highest];
        mysql_key_input[1] = "highest_coin";
        mysql_key_input[2] = "highest_value";
        mysql_input[1] = "\"" + this.highest_coin_name + "\"";
        mysql_input[2] = String.valueOf(this.highest_coin_percent);

        //System.out.println(mysql_input[1] + ", " + this.highest_coin_name);

        mysqlFunc.insert_data("ai_result", mysql_key_input, mysql_input);
    }





}
