import base64
import hashlib
import hmac
import json
import time
import os
import sys
from urllib.request import urlopen, Request

class Coinone_API:

    ACCESS_TOKEN = '225efe50-40a3-4665-8e09-6f95f5df0905'
    SECRET_KEY = 'c8fa018e-b094-4b58-bb0f-47a89fa09c8e'
    UPPERCASE_SECRET_KEY = SECRET_KEY.upper()
    HOST = 'https://api.coinone.co.kr/'
    price = 1000
    qty = 1
    currency = 'btc'

    def __init__(self):
        pass

    def get_info(self, price, qty, currency):
        payload = self.get_base_payload(self)
        payload.update({
            'price': price,
            'qty': qty,
            'currency': currency
        })
        return payload


    def get_base_payload(self):
        return {
            'access_token': self.ACCESS_TOKEN,
        }

    def str_2_byte(self, s, encode='utf-8'):
        return bytes(s, encode)

    def get_encoded_payload(self, payload):
        payload['nonce'] = int(time.time() * 1000)
        dumped_json = json.dumps(payload)
        encoded_json = base64.b64encode(self.str_2_byte(self, dumped_json))
        return encoded_json

    def get_signature(self, encoded_payload):
        signature = hmac.new(self.str_2_byte(self, self.UPPERCASE_SECRET_KEY), encoded_payload, hashlib.sha512)
        return signature.hexdigest()

    def get_response(self, url, payload):
        encoded_payload = self.get_encoded_payload(self, payload)
        signature = self.get_signature(self, encoded_payload)
        headers = {
            'Content-Type': 'application/json',
            'X-COINONE-PAYLOAD': encoded_payload,
            'X-COINONE-SIGNATURE': signature,
        }
        api_url = self.HOST + url
        req = Request(api_url, data=encoded_payload, headers=headers, method='POST')

        with urlopen(req) as res:
            data = res.read().decode('utf-8')
            return json.loads(data)

    def buy(self, price, qty, currency):
        payload = self.get_info(self, price, qty, currency)
        data = self.get_response(self, 'v2/order/limit_buy/', payload)
        print(data)

    def sell(self, price, qty, currency):
        payload = self.get_info(self, price, qty, currency)
        data = self.get_response(self, 'v2/order/limit_sell/', payload)
        print(data)

    def balance(self):
        payload = self.get_base_payload(self)
        data = self.get_response(self, 'v2/account/balance/', payload)
        with open('./balance.json', 'w', encoding="utf-8") as make_file:
            json.dump(data, make_file, ensure_ascii=False, indent="\t")
        print(data)


if __name__ == "__main__":
    test = Coinone_API

    #파일 읽어서 1일때는 사기, 2일때는 팔기, 3일때는 balance 리턴
    #파일 타입은

    ###### avaliable.txt
    #   1
    #   10000
    #   0.1
    #   btc
    ########
    # 형식으로 되어 있음
    # 이건 팀원과의 회의가 완료되야 파일 위치 지정 가능




    while True:
        #지정 파일을 열음
        file = open('FILEPATH', 'r')
        type = int(file.readline())

        #0일때는 파이썬이 할 일이 없는 경우 (예외코드)
        if type == 0:
            file.close()
            pass
        
        #1일때는 지정된 값을 파일에서 읽어와 "사는" 부분
        elif type == 1:
            price = int(file.readline())
            qty = float(file.readline())
            currency = file.readline()
            test.buy(test, price, qty, currency)
            file.close()
            creater = open('FILEPATH', 'w')
            creater.write(str(0))
            creater.close()
            
        #2일때는 지정된 값을 파일에서 읽어와 "파는" 부분
        elif type == 2:
            price = int(file.readline())
            qty = float(file.readline())
            currency = file.readline()
            test.sell(test, price, qty, currency)
            file.close()
            creater = open('FILEPATH', 'w')
            creater.write(str(0))
            creater.close()
            
        #3일때는 사용자 정보를 json으로 넘겨주는 부분
        elif type == 3:
            os.system('rm -rf ./balance.json')
            test.balance(test)
            file.close()
            creater = open('FILEPATH', 'w')
            creater.write(str(0))
            creater.close()
