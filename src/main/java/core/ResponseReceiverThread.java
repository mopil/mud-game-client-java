package core;

import dto.Response;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ResponseReceiverThread extends Thread {
    private final TCPClient tcpClient;
    private final Field field;
    private final String username;

    @Override
    public void run() {
        while (true) {
            Response response = tcpClient.receiveResponse();
            if (response == null) {
                System.out.println("서버 연결 종료");
                break;
            }
            if (response.message != null)
                System.out.println(response.message);
            else field.show();
            System.out.print(username + " >>> ");
        }
    }
}
