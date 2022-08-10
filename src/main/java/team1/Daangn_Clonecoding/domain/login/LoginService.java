package team1.Daangn_Clonecoding.domain.login;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team1.Daangn_Clonecoding.domain.member.Member;
import team1.Daangn_Clonecoding.domain.member.memberrepository.MemberRepository;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    public Member login(String loginId, String loginPw) {
        return memberRepository.findByLoginId(loginId)
                .filter(m -> m.getLoginPw().equals(loginPw))
                .orElse(null);
    }
}
