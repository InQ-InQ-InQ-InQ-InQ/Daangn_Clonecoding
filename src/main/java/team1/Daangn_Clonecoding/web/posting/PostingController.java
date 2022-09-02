package team1.Daangn_Clonecoding.web.posting;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import team1.Daangn_Clonecoding.domain.member.Member;
import team1.Daangn_Clonecoding.domain.member.memberrepository.MemberRepository;
import team1.Daangn_Clonecoding.domain.posting.Posting;
import team1.Daangn_Clonecoding.domain.posting.postingrepository.PostingRepository;
import team1.Daangn_Clonecoding.domain.posting.postingservice.PostingService;
import team1.Daangn_Clonecoding.web.SessionConst;
import team1.Daangn_Clonecoding.web.exception.NotExistPkException;
import team1.Daangn_Clonecoding.domain.posting.dto.PostingDetailResponse;
import team1.Daangn_Clonecoding.domain.posting.dto.PostingForm;
import team1.Daangn_Clonecoding.domain.posting.dto.PostingResponse;
import team1.Daangn_Clonecoding.web.response.Success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posting")
public class PostingController {

    private final PostingRepository postingRepository;
    private final MemberRepository memberRepository;
    private final PostingService postingService;

    @PostMapping("/new")
    public Success newPosting(@ModelAttribute PostingForm form,
                              @SessionAttribute(SessionConst.LOGIN_MEMBER) Long memberId) {

        // 파일 저장 및 posting 생성 후 저장
        postingService.newPosting(form, memberId);

        return new Success(true);
    }

    @GetMapping//디폴트 페이징은 로그인 된 회원의 city 로 찾고 createdDate 로 정렬하여 페이징한다.
    public Slice<PostingResponse> findPostingByPaging(@SessionAttribute(SessionConst.LOGIN_MEMBER) Long memberId,
                                                      @PageableDefault(sort = "createdDate") Pageable pageable) {

        return postingService.findPagingPosting(memberId, pageable);
    }

    @GetMapping("{postingId}")
    public PostingDetailResponse findPostingDetail(@PathVariable Long postingId,
                                                   @SessionAttribute(SessionConst.LOGIN_MEMBER) Long memberId) {

        return postingService.findDetailPosting(postingId, memberId);
    }
}
