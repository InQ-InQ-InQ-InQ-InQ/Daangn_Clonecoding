package team1.Daangn_Clonecoding.web.member;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import team1.Daangn_Clonecoding.domain.member.Address;
import team1.Daangn_Clonecoding.domain.member.Member;
import team1.Daangn_Clonecoding.domain.member.memberrepository.MemberRepository;
import team1.Daangn_Clonecoding.web.member.dto.JoinForm;
import team1.Daangn_Clonecoding.web.member.exception.DuplicatedException;
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
            throw new DuplicatedException("Duplicated_LoginId");
        }

        return new Success(true);
    }

    @PostMapping("/join/nicknameDu")
    public Success nicknameDuplicationCheck(@RequestParam String nickname) {

        Optional<Member> optionalMember = memberRepository.findByNickname(nickname);
        Member findMember = optionalMember.orElse(null);

        if (findMember != null) {
            throw new DuplicatedException("Duplicated_Nickname");
        }

        return new Success(true);
    }
}
