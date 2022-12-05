package core;

import dto.Request;
import dto.Response;
import util.JsonConverter;

import java.io.DataInputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TCPClient {

    public Socket socket;
    private final JsonConverter jsonConverter = JsonConverter.getInstance();

    public void sendRequest(Request request) {
        try {
            OutputStream os = socket.getOutputStream();
            String json = jsonConverter.toJson(request);
            os.write(json.getBytes());
            os.flush();
        } catch (Exception e) {
            System.out.println("메시지 전송 에러");
        }
    }

    public Response receiveResponse() {
        try {
            String jsonResponse =  new DataInputStream(socket.getInputStream()).readUTF();
            return jsonConverter.toObject(jsonResponse, Response.class);
        } catch (Exception e) {
            System.out.println("메시지 수신 에러");
            return null;
        }
    }

    public Response request(Request request) {
        sendRequest(request);
        Response response = receiveResponse();
        if (response == null) {
            System.out.println("서버 통신 실패, response == null");
        }
        return response;
    }

    public TCPClient() {
        try {
            socket = new Socket("localhost", 7000);
            System.out.println("MUD 게임 서버 연결 완료!");
        } catch (Exception e) {
            System.out.println("MUD 게임 서버에 연결할 수 없습니다. 클라이언트를 종료합니다.");
            System.exit(0);
        }
    }
}
