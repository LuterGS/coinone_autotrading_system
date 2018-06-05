CryptoCurrency AutoTrading System
==================================

현재 버전 : 0.1.3-Test_Beta 

제작자: LuterGS, emiling, Jonnson_, Randomshot

# 설명
 * 이 프로그램은 현재 테스트 베타 버전입니다. 언제든지 비정상 동작을 할 가능성이 있습니다. 
 * (TEMP) 현재 인공신경망 데이터가 완전히 올라가지 않았습니다.
 
# 서버 세팅
 * 이 프로그램은 리눅스 서버 두 개가 있어야 동작합니다.
   1. 한 대의 리눅스 서버는 외부와 통신하는 서버입니다. 이 서버를 A라고 합니다.
   2. 다른 한 대의 리눅스 서버는 내부 DB를 관리하는 서버입니다. 이 서버를 B라고 합니다.
   
 * 서버 A, B에 최소한으로 필요한 것들은 다음과 같습니다.
   * 서버 A : 
     * Tomcat (버전 8 이상)
     * Java (Java 8 SE 이상, Oracle JDK)  
     * Python (Version 3.5 or above)
     * Python TensorFlow module
   * 서버 B :
     * MySQL
     
# 프로그램 세팅
 * 서버 B의 스키마와 테이블 구성은 MySQL.txt를 참고해주세요.
 * 서버 A의 jar 실행 위치에서 LATEST_DATA.txt와, Train_avaliable.txt를 만들어주세요.
   * LATEST_DATA.txt는 MySQL에서 coin의 마지막 Date를 가르킵니다. 따라서 처음엔 1로, 이후엔 변경하지 말아주세요.
   * Train_avaliable.txt는 트레이닝을 시켜도 되는지 여부를 가르키는 파일입니다. 처음엔 0으로, 이후엔 변경하지 말아주세요.
 * UserData내의 파일 2개는 각각 코인원의 key값과, mysql의 정보를 저장하는 파일입니다.
 * trained_data 내에는 인공지능 신경망의 정보가 담겨있습니다.
 * trade.sh에 755 권한을 주셔야 합니다.  
 
 * 한 폴더 내에 아래 트리처럼 파일을 위치시켜주세요.
   * CryptoTrading_v0.1.3 (Folder)
     * cryptotrading_v0.1.3.kar (JAR File)
     * coin_cnn.py (Python File)
     * LATEST_DATA.txt (Text File)
     * Train_avaliable.txt (Text File)
     * trade.sh (Shell Scrpit File)
     * UserData (Folder)
       * coinone_info.txt (Text File)
       * mysql_info.txt (Text File)
     * trained_data (Folder)
       * needed files
       
 * 이후, 리눅스 명령어를 써서 실행시키면 됩니다.
    <pre><code> trade.sh chmod 755 
    nohup ./trade.sh > log_out.txt 2> log_error.txt < /dev/null &</code></pre>
    
    
# 프로그램 동작 방식
 * 스레드 2개가 돌아갑니다.
   * 스레드 1 : 코인 데이터를 5분에 하나씩 ticker_database.coin에 저장합니다.
   * 스레드 2 : 코인 데이터를 토대로 인공신경망으로 가격을 판단, 거래합니다.
   
# 개발자들을 위해
 * Java 프로젝트는 외부 디펜던시를 사용합니다. pom.xml을 Maven으로 필요한 외부 라이브러리를 가져와주세요.
 * Python 파일은 인공신경망을 사용합니다. 구동하기 위해서는 TensorFlow Module이 필요합니다.
 