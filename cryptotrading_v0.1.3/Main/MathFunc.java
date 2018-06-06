package Main;

public class MathFunc {

    //string으로 저장되어있는 double형 실수를 소수점 4자리까지만 자름
    public static double string_toDouble_4(String input){

        Double temp = Double.valueOf(input);
        int integer_temp = (int)(temp * 10000);
        temp = ((double)integer_temp) / 10000;

        return temp;
    }

    //double형 실수를 소수점 4자리까지만 자름
    public static double double_toDouble_4(double input){

        int integer_temp = (int)(input * 10000);

        return ((double)integer_temp) / 10000;
    }

    //받은 double형 배열 중에서 가장 높은 값을 가지고 있는 인덱스 반환
    public static int get_highest_value(double[] input){

        int num = 0, a;
        double checker = -1000;

        for(a = 0; a < input.length; a++){
            if(input[a] > checker){
                num = a;
                checker = input[a];
            }
        }

        return num;
    }
}
