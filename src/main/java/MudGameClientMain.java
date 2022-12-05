import core.Field;
import core.RequestSenderThread;
import core.ResponseReceiverThread;
import core.TCPClient;
import dto.Request;
import dto.Response;

import java.util.Scanner;

public class MudGameClientMain {
     private static String login(TCPClient tcpClient, Field field) {
          Scanner scan = new Scanner(System.in);
          System.out.print("로그인할 이름을 입력해 주세요: ");
          String username = scan.nextLine();
          Request loginReq = new Request("login", username);
          Response loginResponse = tcpClient.request(loginReq);
          if (loginResponse == null) {
               System.out.println("로그인 실패! 서버가 너무 혼잡합니다!");
               System.out.println("클라이언트를 종료합니다..");
               System.exit(0);
          } else {
               System.out.println(loginResponse.message);
               field.update(loginResponse);
          }
          return username;
     }

     public static void main(String[] args) {
          TCPClient tcpClient = new TCPClient();
          Field field = new Field();
          String username = login(tcpClient, field);

          // 게임 접속 후 진행
          new RequestSenderThread(tcpClient, field, username).start();
          new ResponseReceiverThread(tcpClient, field, username).start();
     }


}
