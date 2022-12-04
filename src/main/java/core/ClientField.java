package core;

import dto.Coordinate;
import dto.ResponseDto;

public class ClientField {

    String[][] field = new String[30][30];

    public ClientField() {
        for (int x = 0; x < 30; x++)
            for (int y = 0; y < 30; y++)
                field[x][y] = "_";
    }

    public void show() {
        for (int x = 0; x < 30; x++) {
            for (int y = 0; y < 30; y++) {
                System.out.printf("%s\t", field[x][y]);
            }
            System.out.println();
        }
    }

    public void update(ResponseDto responseDto) {
        for (Coordinate coord : responseDto.coords) {
            field[coord.x][coord.y] = coord.who;
        }
    }
}
