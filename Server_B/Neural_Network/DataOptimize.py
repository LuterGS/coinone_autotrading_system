import numpy
import random

class datamaker:

    raw_input = 0
    bch_length = 68664
    btc_length = 101356
    etc_length = 103185
    eth_length = 96733
    ltc_length = 98022
    xrp_length = 89867
    coin_length = [bch_length, btc_length, etc_length, eth_length, ltc_length, xrp_length]

    input_array = 0
    answer_array = 0

    def make_random_num(self, length):
        list = []
        for i in range (0, length):
            list.append(i)
        random.shuffle(list)
        return list


    def select_length(self, num):
        if num == 1:
            return self.bch_length
        elif num == 2:
            return self.btc_length
        elif num == 3:
            return self.etc_length
        elif num == 4:
            return self.eth_length
        elif num == 5:
            return self.ltc_length
        elif num == 6:
            return self.xrp_length



    def init_file(self, name):
        self.raw_input = open("Data/" + name + "_difference.txt", 'r')

    def array_define_length(self, length):
        self.input_array = numpy.zeros((length, 20))
        self.answer_array = numpy.zeros((length, 2003))

    def read_all(self, length, name):
        self.init_file(name)
        self.array_define_length(length)

        i = 1
        for i in range(0, length):
            self.one_line_read(i)

        numpy.save("Data/" + name + "_input.npy", self.input_array)
        numpy.save("Data/" + name + "_answer.npy", self.answer_array)


    def one_line_read(self, linenum):
        oneline = self.raw_input.readline().split(',')

        self.input_arr(oneline, linenum)
        self.answer_arr(float(oneline[20]), linenum)

        #print(self.input_array[linenum])
        #print(self.answer_array[linenum])

    def input_arr(self, oneline, linenum):
        for i  in range(0, 20):
            self.input_array[linenum][i] = float(oneline[i])
        # print(linenum, self.input_array[linenum])

    def answer_arr(self, input, linenum):


        input_dadum = int(input * 100)
        input = input_dadum/100

        #print(input)
        insert = (input + 10.01) * 100
        insert_int = int(insert)
        #print(insert_int)
        #print(input)

        if(input < -10):
            #print("reach case1 ", input_2)
            self.answer_array[linenum][0] = 1
        elif(input > 10):
            #print("reach case2 ", input_2)
            self.answer_array[linenum][2002] = 1
        else:
            self.answer_array[linenum][insert_int] = 1

        #for b in range(0, 2003):
        #    if self.answer_array[linenum][b] == 1:
        #        print("answer is ", b)


if __name__ == "__main__":
    train = datamaker()
    #train.make_random_num(train.bch_length)
    train.read_all(train.coin_length[0], "bch")
    print("BCH COMPLETE")
    train.read_all(train.coin_length[1], "btc")
    print("BTC COMPLETE")
    train.read_all(train.coin_length[2], "etc")
    print("ETH COMPLETE")
    train.read_all(train.coin_length[3], "eth")
    print("ETC COMPLETE")
    train.read_all(train.coin_length[4], "ltc")
    print("LTC COMPLETE")
    train.read_all(train.coin_length[5], "xrp")
    print("XRP COMPLETE")