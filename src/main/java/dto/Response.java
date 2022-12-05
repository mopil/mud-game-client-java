package dto;

import lombok.Data;

import java.util.List;

@Data
public class Response {
    public String message;
    public List<Coordinate> coords;
}
