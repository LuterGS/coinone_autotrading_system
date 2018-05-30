CryptoCurrency AutoTrading System
==================================

Current Version : 0.1.2-PRE_ALPHA-v2

Made by LuterGS, emiling, Jonnson_, Randomshot

# Important!
 * This program is not functional, yet.
 * This program is still in development status.
 
# Server Setting
 * This program need two linux server.
   1. One server's job is get/send info with outer web. We will call this Server A.
   2. Another Server's job is maintaining DB. We will call this server B.
   
 * Minimum requirement for Server
   * Server A :
     * NFS (Network File Storage) Service
     * Tomcat (Version 8 or above)
     * Java (Java 8 SE or above, Oracle JDK)
   * Server B :
     * MySQL
     * Java (Java 8 SE or above, Oracle JDK)
     * Python (Version 3.5 or above)
     * Python TensorFlow module
     
# Program Setting
 * Server B's MySQL Schema and Table configuration
   * ticker_database (Schema)
     * coin (Table)
       * Date (INT, Primary Key, Not NULL)
       * eos (INT)
       * bch (INT)
       * qtum (INT)
       * iota (INT)
       * ltc (INT)
       * etc (INT)
       * btg (INT)
       * btc (INT)
       * omg (INT)
       * eth (INT)
       * xrp (INT)
     * result (Table)
       * Date (INT, Primary Key)
       * coin (Char, (4))
       * percent (Float, (5,2)) 
       
   * At least one data will be needed at each table. Use following console command in MySQL.
   <pre><code> insert into ticker_database.coin(Date) value (0); 
    insert into ticker_database.result(Date) value (0); </code></pre>
      
 * Server A will be hosting NFS. location is 
 
   <pre><code> /home/lutergs_server/NFS </code></pre>
    In that folder, another two folder will be needed.
   <pre><code> /home/lutergs_server/NFS/result
    /home/lutergs_server/NFS/result/traindata</code></pre>
   
 * We will be presenting one script file (.sh) to execute program, when we finish develop.

# For Developer
 * "Out Server/Get_Set_Buy_Java" is Apache Maven project.
 * "Inner Server/NeuralNetwork_Python" is using Python and TensorFlow.



***************************************************************************
</br>
</br>
</br>



현재 버전 : 0.1.2-PRE_ALPHA-v2

제작자: LuterGS, emiling, Jonnson_, Randomshot

# 중요!
 * 이 프로그램은 현재 동작하지 않습니다.
 * 이 프로그램은 아직 개발중입니다.
 
# 서버 세팅
 * 이 프로그램은 리눅스 서버 두 개가 있어야 동작합니다.
   1. 한 대의 리눅스 서버는 외부와 통신하는 서버입니다. 이 서버를 A라고 합니다.
   2. 다른 한 대의 리눅스 서버는 내부 DB를 관리하는 서버입니다. 이 서버를 B라고 합니다.
   
 * 서버 A, B에 최소한으로 필요한 것들은 다음과 같습니다.
   * 서버 A : 
     * NFS 기능
     * Tomcat (버전 8 이상)
     * Java (Java 8 SE 이상, Oracle JDK)  
   * 서버 B :
     * MySQL
     * Java (Java 8 SE 이상, Oracle JDK)
     * Python (3.5 이상)
     * Python TensorFlow module
     
# 프로그램 세팅
 * 서버 B의 스키마와 테이블 구성은 다음을 따릅니다.
   * ticker_database (Schema)
     * coin (Table)
       * Date (INT, Primary Key, Not NULL)
       * eos (INT)
       * bch (INT)
       * qtum (INT)
       * iota (INT)
       * ltc (INT)
       * etc (INT)
       * btg (INT)
       * btc (INT)
       * omg (INT)
       * eth (INT)
       * xrp (INT)
     * result (Table)
       * Date (INT, Primary Key)
       * coin (Char, (4))
       * percent (Float, (5,2))
       
   * 적어도 1개의 데이터가 각각의 테이블에 필요합니다. MySQL에서 해당 명령어로 넣을 수 있습니다.
   <pre><code> insert into ticker_database.coin(Date) value (0); 
    insert into ticker_database.result(Date) value (0); </code></pre>
       
 * 서버 A에서 NFS를 호스팅합니다. 호스팅 위치는 현 프로그램은
  
   <pre><code> /home/lutergs_server/NFS </code></pre>
   라고 가정합니다. 해당 폴더 안에는 두 개의 폴더가 위치하고 있습니다.
   <pre><code> /home/lutergs_server/NFS/result
    /home/lutergs_server/NFS/result/traindata</code></pre>
    
 * 완성되면 하나의 스크립트 파일로 (.sh) 실행할 수 있도록 제공할 계획입니다.
 
# 개발자들을 위해
 * "Out Server/Get_Set_Buy_Java" 는 메이븐 프로젝트입니다.
 * "Inner Server/NeuralNetwork_Python" 에서 TensorFlow를 사용합니다.