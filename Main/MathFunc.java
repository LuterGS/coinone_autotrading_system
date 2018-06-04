package Main;

public class MathFunc {

    public static double string_toDouble_4(String input){

        Double temp = Double.valueOf(input);
        int integer_temp = (int)(temp * 10000);
        temp = ((double)integer_temp) / 10000;

        return temp;
    }

    public static double double_toDouble_4(double input){

        int integer_temp = (int)(input * 10000);

        return ((double)integer_temp) / 10000;
    }

    public static double double_toDouble_6(double input){

        int integer_temp = (int)(input * 1000000);

        return ((double)integer_temp) / 1000000;
    }

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
