package team1.Daangn_Clonecoding.web.posting;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import team1.Daangn_Clonecoding.domain.posting.Posting;
import team1.Daangn_Clonecoding.domain.posting.postingrepository.PostingRepository;
import team1.Daangn_Clonecoding.domain.posting.postingservice.PostingService;
import team1.Daangn_Clonecoding.web.SessionConst;
import team1.Daangn_Clonecoding.domain.posting.dto.PostingDetailResponse;
import team1.Daangn_Clonecoding.domain.posting.dto.PostingForm;
import team1.Daangn_Clonecoding.domain.posting.dto.PostingResponse;
import team1.Daangn_Clonecoding.web.response.BasicResponse;
import team1.Daangn_Clonecoding.web.response.Success;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posting")
public class PostingController {

    private final PostingRepository postingRepository;
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

    @PutMapping("/buy") //게시물 상태 구매완료로 변경
    public Success buy(@SessionAttribute(SessionConst.LOGIN_MEMBER) Long memberId,
                       @RequestParam Long postingId) {

        postingService.buy(memberId, postingId);

        return new Success(true);
    }

    @GetMapping("/purchase_log") //판매목록 조회
    public BasicResponse<List<PostingResponse>> findPurchaseLogs(@SessionAttribute(SessionConst.LOGIN_MEMBER) Long memberId) {

        List<Posting> postings = postingRepository.findByBuyer(memberId);

        List<PostingResponse> result = postings.stream().map((PostingResponse::new))
                .collect(Collectors.toList());

        return new BasicResponse<>(result);
    }

    @GetMapping("/sale_log") //구매목록 조회
    public BasicResponse<List<PostingResponse>> findSaleLogs(@SessionAttribute(SessionConst.LOGIN_MEMBER) Long memberId) {

        List<Posting> postings = postingRepository.findBySeller(memberId);

        List<PostingResponse> result = postings.stream().map(PostingResponse::new)
                .collect(Collectors.toList());

        return new BasicResponse<>(result);
    }
}
