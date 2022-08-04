package team1.Daangn_Clonecoding.web.member;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import team1.Daangn_Clonecoding.domain.member.Address;
import team1.Daangn_Clonecoding.domain.member.Member;
import team1.Daangn_Clonecoding.domain.member.memberrepository.MemberRepository;
import team1.Daangn_Clonecoding.web.member.dto.JoinForm;
import team1.Daangn_Clonecoding.web.member.exception.DuplicatedLoginIdException;
import team1.Daangn_Clonecoding.web.response.Success;

import java.util.Optional;

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

    @PostMapping("/join/loginIdDu")
    public Success loginIdDuplicationCheck(@RequestParam String loginId) {

        Optional<Member> optionalMember = memberRepository.findByLoginId(loginId);
        Member findMember = optionalMember.orElse(null);

        if (findMember != null) {
            throw new DuplicatedLoginIdException("로그인 아이디 중복");
        }

        return new Success(true);
    }


}
