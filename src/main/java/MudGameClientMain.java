import core.ClientField;
import core.CommandProcessor;
import core.TCPClient;
import dto.RequestDto;
import dto.ResponseDto;

import java.util.Scanner;

public class MudGameClientMain {
     public static void main(String[] args) {
          TCPClient tcpClient = new TCPClient();
          ClientField field = new ClientField();
          Scanner scan = new Scanner(System.in);
          // 로그인
          System.out.print("로그인할 이름을 입력해 주세요: ");
          String username = scan.nextLine();
          RequestDto loginReq = new RequestDto("login", username);
          ResponseDto loginResponse = tcpClient.request(loginReq);
          System.out.println(username + "로 로그인 되었습니다!");
          field.update(loginResponse);
          field.show();

          // 게임 접속 후 진행
          while (true) {
               System.out.print(username + " >>> ");
               String outMessage = scan.nextLine();
               System.out.println("보낸 메시지 : " + outMessage);

               // 명령어 파싱 처리
               CommandProcessor commandProcessor = CommandProcessor.getInstance();
               RequestDto request = commandProcessor.process(outMessage);

               // 서버 통신
               ResponseDto response = tcpClient.request(request);
               if (response == null) {
                    System.out.println("서버 연결 종료");
                    break;
               }
               System.out.println("받은 메시지 : " + response);
               field.show();
          }
     }
}
