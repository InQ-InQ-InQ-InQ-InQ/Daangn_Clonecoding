package team1.Daangn_Clonecoding.web.login;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import team1.Daangn_Clonecoding.domain.login.LoginService;
import team1.Daangn_Clonecoding.domain.member.Member;
import team1.Daangn_Clonecoding.web.SessionConst;
import team1.Daangn_Clonecoding.web.login.dto.LoginForm;
import team1.Daangn_Clonecoding.web.response.Success;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "로그인 아이디와 비밀번호를 입력받아 로그인을 한다.")
    public Success login(@ModelAttribute LoginForm loginForm, HttpServletRequest request) {

        Member loginMember = loginService.login(loginForm.getLoginId(), loginForm.getLoginPw());

        HttpSession session = request.getSession(true);
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
        return new Success(true);
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃", description = "로그아웃을 한다.")
    public Success logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return new Success(true);
    }
}
