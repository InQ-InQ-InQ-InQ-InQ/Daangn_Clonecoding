package team1.Daangn_Clonecoding.web.member;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import team1.Daangn_Clonecoding.domain.member.Address;
import team1.Daangn_Clonecoding.domain.member.Member;
import team1.Daangn_Clonecoding.domain.member.memberrepository.MemberRepository;
import team1.Daangn_Clonecoding.web.member.dto.JoinForm;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberRepository memberRepository;

    @PostMapping("/join")
    public Success join(@ModelAttribute JoinForm joinForm) {

        Address address = new Address(joinForm.getCity(), joinForm.getCountry(), joinForm.getDistrict());
        Member member = new Member(joinForm.getName(), joinForm.getNickname(), joinForm.getLoginId(),
                joinForm.getLoginPw(), address);

        memberRepository.save(member);

        return new Success(true);
    }

    @Data
    static class Success {
        private boolean suc;

        public Success(boolean suc) {
            this.suc = suc;
        }
    }

}
