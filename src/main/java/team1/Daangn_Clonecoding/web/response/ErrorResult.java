package team1.Daangn_Clonecoding.web.response;

import lombok.Data;

@Data
public class ErrorResult {

    private boolean suc;
    private String code;
    private String message;

    public ErrorResult(boolean suc ,String code, String message) {
        this.suc = suc;
        this.code = code;
        this.message = message;
    }
}
