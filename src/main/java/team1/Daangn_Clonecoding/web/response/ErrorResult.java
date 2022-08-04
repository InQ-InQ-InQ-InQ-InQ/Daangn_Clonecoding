package team1.Daangn_Clonecoding.web.response;

import lombok.Data;

@Data
public class ErrorResult {

    String type;
    String message;

    public ErrorResult(String type, String message) {
        this.type = type;
        this.message = message;
    }
}
