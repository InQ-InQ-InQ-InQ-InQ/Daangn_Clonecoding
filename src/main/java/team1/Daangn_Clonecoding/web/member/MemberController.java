package team1.Daangn_Clonecoding.web.member;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team1.Daangn_Clonecoding.domain.member.Address;
import team1.Daangn_Clonecoding.domain.member.Member;
import team1.Daangn_Clonecoding.domain.member.memberrepository.MemberRepository;
import team1.Daangn_Clonecoding.domain.member.dto.MemberRequest;
import team1.Daangn_Clonecoding.web.SessionConst;
import team1.Daangn_Clonecoding.web.exception.DuplicatedException;
import team1.Daangn_Clonecoding.web.exception.NotExistPkException;
import team1.Daangn_Clonecoding.web.member.dto.MemberDetailResponse;
import team1.Daangn_Clonecoding.web.member.dto.MemberResponse;
import team1.Daangn_Clonecoding.web.member.dto.SimpleMemberSuccessResponse;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberRepository memberRepository;

    @PostMapping
    @Operation(summary = "회원가입", description = "회원정보를 받아 회원가입을 한다.")
    public ResponseEntity<SimpleMemberSuccessResponse> join(@ModelAttribute MemberRequest memberRequest) {

        Address address = new Address(memberRequest.getCity(), memberRequest.getTown());
        Member member = Member.createMember(memberRequest.getName(), memberRequest.getNickname(), memberRequest.getPhoneNumber(), memberRequest.getLoginId(),
                memberRequest.getLoginPw(), address);

        memberRepository.save(member);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Location", "/api/members/" + member.getId());

        return new ResponseEntity<>(new SimpleMemberSuccessResponse(member.getId()), headers, HttpStatus.CREATED);
    }

    @GetMapping("/validation/loginId")
    @Operation(summary = "아이디 중복 검사", description = "로그인 아이디를 받아서 중복검사를 한다.")
    public ResponseEntity<Void> loginIdDuplicationCheck(@RequestParam String loginId) {

        //TODO 에러 메세지 통일 상수 뽑기 혹은 국제화
        Optional<Member> optionalMember = memberRepository.findByLoginId(loginId);
        optionalMember.orElseThrow(() -> new DuplicatedException("중복된 로그인 아이디 입니다."));

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/validation/nickname")
    @Operation(summary = "닉네임 중복 검사", description = "닉네임을 받아서 중복검사를 한다.")
    public ResponseEntity<Void> nicknameDuplicationCheck(@RequestParam String nickname) {

        Optional<Member> optionalMember = memberRepository.findByNickname(nickname);
        optionalMember.orElseThrow(() -> new DuplicatedException("중복된 닉네임 입니다."));

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/validation/phoneNumber")
    @Operation(summary = "전화번호 중복 검사", description = "전화번호를 받아서 중복검사를 한다.")
    public ResponseEntity<Void> phoneNumberDuplicationCheck(@RequestParam String phoneNumber) {

        Optional<Member> optionalMember = memberRepository.findByPhoneNumber(phoneNumber);
        optionalMember.orElseThrow(() -> new DuplicatedException("중복된 전화번호 입니다."));

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    @Operation(summary = "기본 회원정보 조회", description = "나의 당근페이지에 필요한 회원정보를 조회한다.")
    public MemberResponse getMemberResponse(@Parameter(description = "세션에서 가져오는 데이터로 값 입력 X")
                                                @SessionAttribute(SessionConst.LOGIN_MEMBER) Long memberId) {

        Member member = findMemberById(memberId);

        return new MemberResponse(member);
    }

    @GetMapping("/detail")
    @Operation(summary = "상세 회원정보 조회", description = "회원 상세정보를 조회한다.")
    public MemberDetailResponse getMemberDetailResponse(@Parameter(description = "세션에서 가져오는 데이터로 값 입력 X")
                                                            @SessionAttribute(SessionConst.LOGIN_MEMBER) Long memberId) {

        Member member = findMemberById(memberId);

        return new MemberDetailResponse(member);
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new NotExistPkException("존재하지 않는 pk 입니다."));
    }
}
