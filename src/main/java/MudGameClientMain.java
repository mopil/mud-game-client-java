import core.Field;
import core.TCPClient;
import dto.RequestDto;
import dto.ResponseDto;

import java.util.Random;
import java.util.Scanner;

public class MudGameClientMain {
     
     public static void showHelp() {
          System.out.println("--- 커맨드 설명서 ---");
          System.out.println("map : 던전 필드를 출력합니다.");
          System.out.println("move x y : 현재 좌표에서 x, y 좌표로 이동합니다. (최대 3칸 까지 가능)");
          System.out.println("users : 현재 서버에 접속중인 유저 이름과 좌표들을 출력합니다.");
          System.out.println("monsters : 몬스터의 좌표를 출력합니다.");
     }

     public static void activeBotMode(TCPClient tcpClient, Field field) {
          System.out.println("BOT 모드를 실행합니다. 1초마다 랜덤 명령어를 수행합니다.");
          String[] commandList = {"users", "monsters", "attack"};
          while (true) {
               String randCmd = commandList[new Random().nextInt(commandList.length)];
               System.out.println("보낸 메시지 : " + randCmd);
               ResponseDto response = tcpClient.request(new RequestDto("cmd", randCmd));
               if (response == null) {
                    System.out.println("서버 연결 종료");
                    break;
               }
               if (response.message != null)
                    System.out.println("받은 메시지 : " + response.message);
               else field.show();
               try {
                    Thread.sleep(1000);
               } catch (Exception ignored) {}
          }
     }

     public static void main(String[] args) {
          TCPClient tcpClient = new TCPClient();
          Field field = new Field();
          Scanner scan = new Scanner(System.in);
          // 로그인
          System.out.print("로그인할 이름을 입력해 주세요: ");
          String username = scan.nextLine();
          RequestDto loginReq = new RequestDto("login", username);
          ResponseDto loginResponse = tcpClient.request(loginReq);
          if (loginResponse == null) {
               System.out.println("로그인 실패! 서버가 너무 혼잡합니다!");
               System.out.println("클라이언트를 종료합니다..");
               System.exit(0);
          } else {
               System.out.println(loginResponse.message);
               field.update(loginResponse);
          }

          // 게임 접속 후 진행
          while (true) {
               System.out.print(username + " >>> ");
               String cmd = scan.nextLine();
               if (cmd.equals("help")) {
                    showHelp();
                    continue;
               } else if (cmd.equals("bot")) activeBotMode(tcpClient, field);

               System.out.println("보낸 메시지 : " + cmd);
               RequestDto request = new RequestDto("cmd", cmd);
               // 서버 통신
               ResponseDto response = tcpClient.request(request);
               if (response == null) {
                    System.out.println("서버 연결 종료");
                    break;
               }
               if (response.message != null)
                    System.out.println("받은 메시지 : " + response.message);
               else field.show();
          }
     }
}
