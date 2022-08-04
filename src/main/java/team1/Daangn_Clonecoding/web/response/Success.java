package team1.Daangn_Clonecoding.web.response;

import lombok.Data;

@Data
public class Success {
    public boolean suc;

    public Success(boolean suc) {
        this.suc = suc;
    }
}
