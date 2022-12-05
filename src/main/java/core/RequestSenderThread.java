package core;

import dto.Request;
import lombok.AllArgsConstructor;

import java.util.Random;
import java.util.Scanner;

@AllArgsConstructor
public class RequestSenderThread extends Thread {
    private final TCPClient tcpClient;
    private final Field field;
    private String username;

    private final Scanner scan = new Scanner(System.in);


    @Override
    public void run() {
        System.out.print(username + " >>> ");
        while (true) {
            String cmd = scan.nextLine();
            if (cmd.equals("help")) {
                showHelp();
                System.out.print(username + " >>> ");
                continue;
            } else if (cmd.equals("bot")) {
                activeBotMode(tcpClient);
                break;
            }
            Request request = new Request("cmd", cmd);
            tcpClient.sendRequest(request);
        }
    }

    public static void showHelp() {
        System.out.println("--- 커맨드 설명서 ---");
        System.out.println("map : 던전 필드를 출력합니다.");
        System.out.println("move x y : 현재 좌표에서 x, y 좌표로 이동합니다. (최대 3칸 까지 가능)");
        System.out.println("users : 현재 서버에 접속중인 유저 이름과 좌표들을 출력합니다.");
        System.out.println("monsters : 몬스터의 좌표를 출력합니다.");
        System.out.println("chat username message : username을 가진 사용자에게 message를 보냅니다.");
    }

    public static void activeBotMode(TCPClient tcpClient) {
        System.out.println("BOT 모드를 실행합니다. 1초마다 랜덤 명령어를 수행합니다.");
        String[] commandList = {"users", "monsters", "attack"};
        while (true) {
            String randCmd = commandList[new Random().nextInt(commandList.length)];
            System.out.println("(To Server) " + randCmd);
            tcpClient.sendRequest(new Request("cmd", randCmd));
            try {
                Thread.sleep(1000);
            } catch (Exception ignored) {
            }
        }
    }
}
