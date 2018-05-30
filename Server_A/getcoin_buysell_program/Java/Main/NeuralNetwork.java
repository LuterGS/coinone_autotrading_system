package Main;

public class NeuralNetwork {

    //testcode, need to improve
    private int[] node_num;


    private int layer;
    private int[][][] weight;



    public NeuralNetwork(int layer){
        this.layer = layer;
        this.node_num = new int[layer];
        this.weight = new int[layer - 1][][];

    }
}
