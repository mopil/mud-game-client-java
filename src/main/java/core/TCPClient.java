package core;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.RequestDto;
import dto.ResponseDto;

import java.io.DataInputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TCPClient {

    private Socket socket;
    private ObjectMapper objectMapper = new ObjectMapper();

    public void send(RequestDto requestDto) {
        try {
            OutputStream os = socket.getOutputStream();
            String json = objectMapper.writeValueAsString(requestDto);
            os.write(json.getBytes());
            os.flush();
        } catch (Exception e) {
            System.out.println("메시지 전송 에러");
        }
    }

    public ResponseDto receive() {
        try {
            String jsonResponse =  new DataInputStream(socket.getInputStream()).readUTF();
            return objectMapper.readValue(jsonResponse, ResponseDto.class);
        } catch (Exception e) {
            System.out.println("메시지 수신 에러");
            return null;
        }
    }

    public ResponseDto request(RequestDto requestDto) {
        send(requestDto);
        ResponseDto response = receive();
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
