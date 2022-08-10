package team1.Daangn_Clonecoding.web.login.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginForm {

    private String loginID;

    private String loginPw;
}
