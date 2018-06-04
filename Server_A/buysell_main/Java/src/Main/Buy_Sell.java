       import java.io.*;
        import java.sql.ResultSet;
        import java.sql.SQLException;
        import java.util.Scanner;

public class Sample implements Runnable {
//사고파는 방식을 교체할 수 있도록 설정

    private Userdata userdata = new Userdata();
    private MySQL mysql = new MySQL();
    private String coinName;
    private double coinChange;
    private int time;

    private boolean instantActivate = false;
    private boolean tradeSwitch = false; // false 면 Buy,true 면 Sell
    private String myCoinName;
    private Double myCoinAmount;
    private Double myCoinChange;
    private int KRW;


    public void run() {

        System.out.println("BUY SELL START");

        /*
        Server A에서 해야할 일은
        먼저 데이터를 10개를 가져옴, 이후 10개의 데이터를 모두 처리하고, 깔끔한 +/- % 형으로 정리하는 것
        정리한 다음 traindata/avaliable.txt 에다가 정보를 줬다고 알림 (1로 변경)

        정보를 받아올 때까지 기다림.
        result/avaliable.txt 가 1이면
        값을 읽어온 후 avaliable.txt를 0으로 변경
        mysql에 출력 data 저장
        result들 삭제
        받아온 값을 이용해 살 코인과 예상 퍼센트 결정




         */
        while (true) {
            //이 함수는 현재 시간에서 코인데이터를 예측후 반납하는 함수임
            judge_coin();



            //코인 구매 (result/available.txt  체크)
            if (tradeSwitch == false) {

                if (dropCheck()== false) {

                    coinBuy();
                    tradeSwitch = true;// 코인 판매 활성화

                }//다 떡락이면 구매X
                }

            //상승 코인 판매
                if (checkChange()) {
                    coinSell();
                    tradeSwitch = false;
                }/*어차피 checkChange()에서 코인이 오르지 않으면 별도의 처리가 없어도
                 자동으로 coinSel()이 실행되지 않고 떡락 코인 판매로 넘어감*/


            //떡락 코인 판매
            if(tradeSwitch) {

                    /*아래의 조건문에서 myCoinChange 애초에 양수가 아니면 dropCheck에서 걸러지므로
                      myCoinChange가 음수의 경우를 고민할 필요가 없음*/
                    if (coinChange < -myCoinChange) {
                        coinSell();
                        tradeSwitch = false;
                        //코인 판매가 성공적으로 이루어지면 코인 구매 매서드 활성화
                    }

            }
        }

        //TEST, 마저 작성해야 함
    }





    void coinBuy() {

            //myCoinAmount = KRW / coinValue;
            myCoinName = coinName;
            myCoinChange = coinChange; //구매당시의 예상 떡상률 저장

            //서버정상작동 확인


    }

    //기존 예상치보다 조금이라도 더 많이 떡상했으면 즉시 판매
  void coinSell() {

        //KRW = myCoinAmount * CoinValue;
        myCoinName = "";
        myCoinAmount = 0.0;

    }

    //기존 예상치보다 떡상인지 체크
    boolean checkChange() {

        if (myCoinChange < coinChange) {return true;}

        return false;

    }

    //실시간 최고 코인이 떡락중인지 체크
    boolean dropCheck() {

        if (coinChange < 0) { return true; }
        else { return false; }
    }

    private void judge_coin(){

        get_trainData();
        wait_train_response();
        read_from_response();
        mysql.insert_resultData(coinName, coinChange);
    }

    // 넘지못하는 4차원의 벽 //

    // 넘지못하는 4차원의 벽 //

    // 넘지못하는 4차원의 벽 //

    // 넘지못하는 4차원의 벽 //

    // 넘지못하는 4차원의 벽 //

    // 넘지못하는 4차원의 벽 //

    // 넘지못하는 4차원의 벽 //

    // 넘지못하는 4차원의 벽 //

    // 넘지못하는 4차원의 벽 //

    // 넘지못하는 4차원의 벽 //


    private void get_trainData() {


        int latest = this.userdata.get_latestnum("./LATEST_DATA.txt");
        double[][] coindata_num;

        ResultSet requested = this.mysql.get_latest_traindata(latest);
        this.time = latest;

        //코인 순서는
        //eos, bch, qtum, iota, ltc, etc, btg, btc, omg, eth, xrp
        write_data(set_mysql_data(read_from_mysql(requested)));
        this.userdata.set_latestnum(1, "/home/lutergs_server/NFS/traindata/avaliable.txt");

        System.out.println("GET_DATA finished");
    }

    private String[][] read_from_mysql(ResultSet requested){

        String[][] coinData_string = new String[11][21];
        int count = 0;

        try {
            while (requested.next()) {
                coinData_string[0][count] = requested.getString("eos");
                coinData_string[1][count] = requested.getString("bch");
                coinData_string[2][count] = requested.getString("qtum");
                coinData_string[3][count] = requested.getString("iota");
                coinData_string[4][count] = requested.getString("ltc");
                coinData_string[5][count] = requested.getString("etc");
                coinData_string[6][count] = requested.getString("btg");
                coinData_string[7][count] = requested.getString("btc");
                coinData_string[8][count] = requested.getString("omg");
                coinData_string[9][count] = requested.getString("eth");
                coinData_string[10][count] = requested.getString("xrp");
                count++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return coinData_string;
    }

    private double[][] set_mysql_data(String[][] input){

        double[][] output = new double[11][20];
        double temp1, temp2;
        int a, b;

        for(a = 0; a < 11; a++){
            for(b = 0; b < 20; b++){
                temp1 = Double.parseDouble(input[a][b]);
                temp2 = Double.parseDouble(input[a][b + 1]);
                output[a][b] = (temp1 - temp2) / temp2 * 100;
            }
        }

        return output;
    }

    //트레이닝 시킬 자료를 저장하는 메소드
    private void write_data(double[][] input){

        File file_output = new File("/home/lutergs_server/NFS/TRAIN_DATA.txt");
        BufferedWriter writer;
        int a, b;

        try {
            writer = new BufferedWriter(new FileWriter(file_output));
            for(a = 0; a < 11; a++){
                for(b = 0; b < 20; b++){
                    writer.write(String.valueOf(input[a][b]));
                    writer.write(",");
                }
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    //트레이닝 완료된 데이터가 있는지 확인하는 메소드
    private void wait_train_response(){

        File file;
        int temp;
        while(true){
            file = new File("/home/lutergs_server/NFS/result/avaliable.txt");
            temp = this.userdata.get_latestnum("/home/lutergs_server/NFS/result/avaliable.txt");
            if(temp == 1) {
                break;
            }
        }
    }

    //트레이닝 완료된 데이터가 있을 때 트레이닝 완료된 데이터를 불러오는 메소드
    private void read_from_response(){

        File file = new File("/home/lutergs_server/NFS/result/result.txt");
        try {
            Scanner scan = new Scanner(file);
            this.coinName = scan.nextLine();
            this.coinChange = Double.parseDouble(scan.nextLine());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        userdata.set_latestnum(0, "/home/lutergs_server/NFS/result/avaliable.txt");



    }
}


