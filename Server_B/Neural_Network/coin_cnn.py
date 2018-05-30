import tensorflow as tf
import numpy
import DataOptimize as knower

class NeuralNetwork:

    learning_rate = 0.001
    data_knower = knower.datamaker()

    INPUT_DATA = numpy.load('bch_input.npy')
    ANSWER_DATA = numpy.load('bch_answer.npy')
    dropout_rate = tf.placeholder(tf.float32)

    #initiate input and anwer
    input = tf.placeholder(tf.float32, [None, 10])
    answer = tf.placeholder(tf.float32, [None, 2003])

    #initiate layer
    hidden1 = tf.Variable(tf.zeros([512]))
    hidden2 = tf.Variable(tf.zeros([512]))
    hidden3 = tf.Variable(tf.zeros([256]))
    hidden4 = tf.Variable(tf.zeros([256]))
    output = tf.Variable(tf.zeros([2003]))

    #initiate weight
    weight_i_h1 = tf.get_variable("weight_i_h1", shape=[10,512], initializer=tf.keras.initializers.he_uniform())
    weight_h1_h2 = tf.get_variable("weight_h1_h2", shape=[512, 512], initializer=tf.keras.initializers.he_uniform())
    weight_h2_h3 = tf.get_variable("weight_h2_h3", shape=[512, 256], initializer=tf.keras.initializers.he_uniform())
    weight_h3_h4 = tf.get_variable("weight_h3_h4", shape=[256, 256], initializer=tf.keras.initializers.he_uniform())
    weight_h4_o = tf.get_variable("weight_h4_o", shape=[256, 2003], initializer=tf.keras.initializers.he_uniform())

    #initiate layer_after_activation
    _hidden1_out = tf.nn.relu(tf.matmul(input, weight_i_h1) + hidden1)
    hidden1_out = tf.nn.dropout(_hidden1_out, dropout_rate)
    _hidden2_out = tf.nn.relu(tf.matmul(hidden1_out, weight_h1_h2) + hidden2)
    hidden2_out = tf.nn.dropout(_hidden2_out, dropout_rate)
    _hidden3_out = tf.nn.relu(tf.matmul(hidden2_out, weight_h2_h3) + hidden3)
    hidden3_out = tf.nn.dropout(_hidden3_out, dropout_rate)
    _hidden4_out = tf.nn.relu(tf.matmul(hidden3_out, weight_h3_h4) + hidden4)
    hidden4_out = tf.nn.dropout(_hidden4_out, dropout_rate)

    output_out = tf.matmul(hidden4_out, weight_h4_o) + output

    #initiate cost/loss & optimizer
    cost = tf.reduce_mean(tf.nn.softmax_cross_entropy_with_logits_v2(logits=output_out, labels=answer))
    optimizer = tf.train.AdamOptimizer(learning_rate=learning_rate).minimize(cost)

    def __init__(self, learning_rate):
        self.learning_rate = learning_rate

    def restore_weight(self, name):
        sess = tf.Session()
        sess.run(tf.global_variables_initializer())
        saver = tf.train.Saver()
        saver.restore(sess, name)
        return sess

    def save_oneline(self, oneline):
        inputer = oneline.split(',')
        output = numpy.zeros((1, 20))
        for a in range(0, 20):
            temp = float(inputer[a])
            temp = temp + 10
            temp = temp / 20
            output[0][a] = temp

        return output


    def test_real(self):
        # 코인 순서는
        # eos, bch, qtum, iota, ltc, etc, btg, btc, omg, eth, xrp

        file = open("/home/lutergs_server/NFS/traindata/TRAIN_DATA.txt", "r")
        eos = self.save_oneline(file.readline())
        bch = self.save_oneline(file.readline())   #
        qtum = self.save_oneline(file.readline())
        ltc = self.save_oneline(file.readline())   #
        etc = self.save_oneline(file.readline())   #
        btg = self.save_oneline(file.readline())
        btc = self.save_oneline(file.readline())   #
        omg = self.save_oneline(file.readline())
        eth = self.save_oneline(file.readline())   #
        xrp = self.save_oneline(file.readline())   #
        result = [0,0,0,0,0,0,0,0,0,0]

        result[0] = self.test(eos, "eos")
        result[1] = self.test(bch, "bch")
        result[2] = self.test(qtum, "qtum")
        result[3] = self.test(ltc, "ltc")
        result[4] = self.test(etc, "etc")
        result[5] = self.test(btg, "btg")
        result[6] = self.test(btc, "btc")
        result[7] = self.test(omg, "omg")
        result[8] = self.test(eth, "eth")
        result[9] = self.test(xrp, "xrp")

        test = -100
        coin_result = 0
        output = "null"

        for a in range(0, 10):
            if result[a] > test:
                test = a
                coin_result = a


        if coin_result == 0:
            output = "eos"
        elif coin_result == 1:
            output = "bch"
        elif coin_result == 2:
            output = "qtum"
        elif coin_result == 3:
            output = "ltc"
        elif coin_result == 4:
            output = "etc"
        elif coin_result == 5:
            output = "btg"
        elif coin_result == 6:
            output = "btc"
        elif coin_result == 7:
            output = "omg"
        elif coin_result == 8:
            output = "eth"
        elif coin_result == 9:
            output = "xrp"

        output = (output - 1001)

        return test, output




    def test(self, input_data, coin_name):

        real_name = coin_name
        if coin_name == "eos":
            real_name = "etc"
        elif coin_name == "qtum":
            real_name = "etc"
        elif coin_name == "btg":
            real_name = "ltc"
        elif coin_name == "omg":
            real_name = "etc"


        sess = self.restore_weight(real_name + ".pd")

        output = sess.run(tf.argmax(self.output_out,1 ), feed_dict={self.input: input_data, self.dropout_rate : 0.7})
        return output[0]



if __name__ == "__main__":
    test = NeuralNetwork(0.001)


    while True:
        file = open("/home/lutergs_server/NFS/traindata/avaliable.txt", 'r')
        line = int(file.readline())

        if line == 1:
            file.close()
            outer = open("/home/lutergs_server/NFS/traindata/avaliable.txt", 'w')
            outer.write(str(0))
            outer.close()
            percent, name = test.test_real(test)
            result = open("/home/lutergs_server/NFS/traindata/result.txt", 'w')
            result.write(name)
            result.write("\n")
            result.write(str(percent))
            result.close()




