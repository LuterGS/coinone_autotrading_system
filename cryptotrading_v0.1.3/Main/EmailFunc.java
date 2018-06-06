package Main;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailFunc {

    public  static void sendMail(String name, String mount, String value, String time, int state) {

        //name : 코인 이름
        //value : 코인 가격
        //time : 거래시각
        //state == 0 구매, state == 1 판매

        //보내는 사람 주소
        String host     = "smtp.naver.com";
        final String user   = "gyeonseok@naver.com";
        final String password  = "$y1K!Nv72";

        //받는 사람 주소
        String to     = "koo04034@gmail.com";

        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        props.put("mail.satmp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", host);
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            message.setSubject("Current Coin Trade (Time: " + time +")" );


            if(state == 0) {
                message.setText("현재 구매 현황(Time: " + time + ")\n\n" + "Coin name:  " + name + "\nCoin mount: " + mount + "\nCoin value: " + value + "\n\n행복한 하루 되세요 ^^");
            }

            else if(state == 1){
                message.setText("현재 판매 현황(Time: " + time + ")\n\n" + "Coin name:  " + name + "\nCoin mount: " + mount + "\nCoin value: " + value + "\n\n행복한 하루 되세요 ^^");
            }
            Transport.send(message);
            System.out.println("message sent successfully...");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
