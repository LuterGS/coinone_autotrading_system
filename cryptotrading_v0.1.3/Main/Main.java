package Main;

public class Main {
    
    public static void main(String[] args){


        Save_CoinData save_coinData = new Save_CoinData();
        Buy_Sell buy_sell = new Buy_Sell();

        Thread thread1 = new Thread(save_coinData);
        Thread thread2 = new Thread(buy_sell);

        thread1.start();
        thread2.start();
    }
}
