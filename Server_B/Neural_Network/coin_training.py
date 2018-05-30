import tensorflow as tf
import numpy
import random
import DataOptimize as knower

class NeuralNetwork:

    learning_rate = 0.001
    data_knower = knower.datamaker()

    INPUT_DATA = numpy.load('Data/etc_input.npy')
    ANSWER_DATA = numpy.load('Data/etc_answer.npy')
    dropout_rate = tf.placeholder(tf.float32)

    #initiate input and anwer
    input = tf.placeholder(tf.float32, [None, 20])
    answer = tf.placeholder(tf.float32, [None, 2003])

    #initiate layer
    hidden1 = tf.Variable(tf.zeros([2500]))
    hidden2 = tf.Variable(tf.zeros([2500]))
    hidden3 = tf.Variable(tf.zeros([1250]))
    hidden4 = tf.Variable(tf.zeros([1250]))
    output = tf.Variable(tf.zeros([2003]))

    #initiate weight
    weight_i_h1 = tf.get_variable("weight_i_h1", shape=[20,2500], initializer=tf.contrib.layers.xavier_initializer())
    weight_h1_h2 = tf.get_variable("weight_h1_h2", shape=[2500, 2500], initializer=tf.contrib.layers.xavier_initializer())
    weight_h2_h3 = tf.get_variable("weight_h2_h3", shape=[2500, 1250], initializer=tf.contrib.layers.xavier_initializer())
    weight_h3_h4 = tf.get_variable("weight_h3_h4", shape=[1250, 1250], initializer=tf.contrib.layers.xavier_initializer())
    weight_h4_o = tf.get_variable("weight_h4_o", shape=[1250, 2003], initializer=tf.contrib.layers.xavier_initializer())

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

    def train(self):
        sess = tf.Session()
        sess.run(tf.global_variables_initializer())

        list_choice = self.data_knower.make_random_num(self.data_knower.etc_length)
        for i in range(0, 5000):
            data_in = numpy.zeros((1,20))
            data_out = numpy.zeros((1,2003))
            for a in range(0, 20):
                data_in[0][a] = self.INPUT_DATA[list_choice[i]][a]
                #print(data_in[0][a], self.INPUT_DATA[i][a])
            #print(data_in)
            for b in range(0, 2003):
                data_out[0][b] = self.ANSWER_DATA[list_choice[i]][b]
                #if data_out[0][b] == 1:
                    #print("answer is ", b)


            feed_dict = {self.input : data_in, self.answer : data_out, self.dropout_rate : 0.7}
            output = sess.run([self.cost, self.optimizer], feed_dict=feed_dict)
            #print("ONE TERM, prediction is", output)
            #print("   ")

        #save current weight
        self.save_weight(sess)
        print("TRAIN FINISHED")


    def save_weight(self, sess):
        saver = tf.train.Saver()
        saver.save(sess, "./etc.pd")


    def restore_weight(self, name):
        sess = tf.Session()
        sess.run(tf.global_variables_initializer())
        saver = tf.train.Saver()
        saver.restore(sess, name)
        return sess


    def test(self):
        sess = self.restore_weight("Data/etc.pd")

        random_num = random.randint(0, self.data_knower.etc_length)
        data_in = numpy.zeros((1,20))
        for a in range (0,20):
            data_in[0][a] = self.INPUT_DATA[random_num][a]
            #print(data_in[0][a], self.INPUT_DATA[random_num][a])
        #print(data_in)

        self.print_answer(random_num)
        output = sess.run(
            tf.argmax(self.output_out, 1), feed_dict={self.input: data_in, self.dropout_rate : 0.7})
        print("Prediction: ", output[0])
        file = open("test_output.txt", 'w')
        file.write(str(output[0]))

    def print_answer(self, num):
        for a in range (0,2003):
            if(self.ANSWER_DATA[num][a] == 1):
                print("ANSWER is ", a)


    def test_real(self, coin_name):
        sess = self.restore_weight(coin_name + ".pd")

        inputData = numpy.load("TEST")
        data_in = numpy.zeros((1,20))
        for a in range(0, 20):
            data_in[0][a] = inputData[0][a]

        output = sess.run(tf.argmax(self.output_out,1 ), feed_dict={self.input: data_in, self.dropout_rate : 0.7})
        file = open("/home/lutergs_server/NFS/result/result.txt")
        file.write(coin_name + "\n")
        file.write(str(output[0]))
        file.close()



if __name__ == "__main__":
    test = NeuralNetwork(0.001)
    print(test.data_knower.etc_length)
    test.train()
    test.test()
    test.test()
    test.test()
    test.test()
    test.test()
    test.test()
    test.test()
    test.test()
    test.test()
    test.test()
    test.test()