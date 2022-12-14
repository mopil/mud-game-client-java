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
            if (cmd.equals("logout")) {
                System.out.println("게임을 종료합니다. 5분내로 동일한 닉네임으로 로그인시 게임을 이어갈 수 있습니다.");
                System.exit(0);
            }
        }
    }

    public static void showHelp() {
        System.out.println("----- 커맨드 설명서 -----");
        System.out.println("[       help     ]\t명령어들을 출력합니다.");
        System.out.println("[       map      ]\t던전 필드를 출력합니다.");
        System.out.println("[       info     ]\t현재 로그인된 사용자 정보를 출력합니다.");
        System.out.println("[     move x y   ]\t현재 좌표에서 x, y 좌표로 이동합니다. (최대 3칸까지 가능하며, 유저가 존재하거나 몬스터가 존재하면 이동할 수 없습니다.)");
        System.out.println("[      users     ]\t현재 서버에 접속중인 유저 이름과 좌표들을 출력합니다.");
        System.out.println("[    monsters    ]\t전체 몬스터 좌표들을 출력합니다.");
        System.out.println("[ chat 사용자 채팅 ]\t'사용자'닉네임을 가진 유저에게 '채팅'을 보냅니다.");
        System.out.println("[   item hp/str  ]\t아이템을 사용합니다. hp 포션은 체력을 10 회복시키고, str 포션은 60초동안 공격력을 3만큼 증가시킵니다.");
        System.out.println("[     logout     ]\t접속을 종료합니다. 5분까지 기존에 플레이하던 게임 정보는 유효합니다.");
    }

    public static void activeBotMode(TCPClient tcpClient) {
        System.out.println("BOT 모드를 실행합니다. 1초마다 랜덤 명령어를 수행합니다.");
        Random random = new Random();
        String randMove = "move " + random.nextInt(0, 30) + " " + random.nextInt(0, 30);
        String[] commandList = {"users", "monsters", "attack", "info", "item hp", "item str", randMove};
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
