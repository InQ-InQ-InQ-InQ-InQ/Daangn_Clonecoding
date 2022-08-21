package team1.Daangn_Clonecoding.web.member;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import team1.Daangn_Clonecoding.domain.member.Address;
import team1.Daangn_Clonecoding.domain.member.Member;
import team1.Daangn_Clonecoding.domain.member.memberrepository.MemberRepository;
import team1.Daangn_Clonecoding.web.member.dto.JoinForm;
import team1.Daangn_Clonecoding.web.exception.DuplicatedException;
import team1.Daangn_Clonecoding.web.response.Success;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberRepository memberRepository;

    @PostMapping("/join")
    public Success join(@ModelAttribute JoinForm joinForm) {

        Address address = new Address(joinForm.getCity(), joinForm.getTown());
        Member member = Member.createMember(joinForm.getName(), joinForm.getNickname(), joinForm.getPhoneNumber(), joinForm.getLoginId(),
                joinForm.getLoginPw(), address);

        memberRepository.save(member);

        return new Success(true);
    }

    @PostMapping("/join/loginIdDu")
    public Success loginIdDuplicationCheck(@RequestParam String loginId) {

        //TODO 에러 메세지 통일 상수 뽑기 혹은 국제화
        Optional<Member> optionalMember = memberRepository.findByLoginId(loginId);
        optionalMember.orElseThrow(() -> new DuplicatedException("중복된 로그인 아이디 입니다."));

        return new Success(true);
    }

    @PostMapping("/join/nicknameDu")
    public Success nicknameDuplicationCheck(@RequestParam String nickname) {

        Optional<Member> optionalMember = memberRepository.findByNickname(nickname);
        optionalMember.orElseThrow(() -> new DuplicatedException("중복된 닉네임 입니다."));

        return new Success(true);
    }

    @PostMapping("/join/phoneNumberDu")
    public Success phoneNumberDuplicationCheck(@RequestParam String phoneNumber) {

        Optional<Member> optionalMember = memberRepository.findByPhoneNumber(phoneNumber);
        optionalMember.orElseThrow(() -> new DuplicatedException("중복된 전화번호 입니다."));

        return new Success(true);
    }
}
