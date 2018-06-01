package Main;

public class Main {

    public static void main(String[] args){


        Save_database test = new Save_database();
        //일정 간격마다 ticker를 MySQL에 저장하는 객체

        Buy_Sell test2 = new Buy_Sell();
        //MySQL에 저장된 DB를 가지고 살 코인을 고르고, 상황에 따라 팔 시기를 선택하는 객체


        Thread thread_1 = new Thread(test);
        Thread thread_2 = new Thread(test2);
        //스레드로 생성 후

        thread_1.start();
        thread_2.start();
        //스레드로 실행

    }
}
