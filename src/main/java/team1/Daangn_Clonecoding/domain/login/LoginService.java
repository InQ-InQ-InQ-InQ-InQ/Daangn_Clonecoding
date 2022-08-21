package team1.Daangn_Clonecoding.domain.login;

import ch.qos.logback.core.LogbackException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team1.Daangn_Clonecoding.domain.member.Member;
import team1.Daangn_Clonecoding.domain.member.memberrepository.MemberRepository;
import team1.Daangn_Clonecoding.web.exception.LoginException;

import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    public Member login(String loginId, String loginPw) {
        return memberRepository.findByLoginId(loginId)
                .filter(m -> m.getLoginPw().equals(loginPw))
                .orElseThrow(() -> new LoginException("로그인 실패"));
    }
}
