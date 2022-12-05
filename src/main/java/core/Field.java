package core;

import dto.Coordinate;
import dto.Response;

public class Field {
    private static final int FIELD_SIZE = 30;

    private final String[][] field = new String[FIELD_SIZE][FIELD_SIZE];

    public Field() {
        clear();
    }

    public void clear() {
        for (int x = 0; x < FIELD_SIZE; x++)
            for (int y = 0; y < FIELD_SIZE; y++)
                field[x][y] = "_";
    }

    public void show() {
        for (int x = 0; x < FIELD_SIZE; x++) {
            for (int y = 0; y < FIELD_SIZE; y++) {
                System.out.printf("%s\t", field[x][y]);
            }
            System.out.println();
        }
    }

    public void update(Response response) {
        clear();
        for (Coordinate coordinate : response.coords) {
            field[coordinate.x][coordinate.y] = coordinate.who;
        }
    }
}
