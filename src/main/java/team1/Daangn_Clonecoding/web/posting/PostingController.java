package team1.Daangn_Clonecoding.web.posting;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/v3/api")
@Api(value = "게시물 컨트롤러")
public class PostingController {

    private final PostingRepository postingRepository;
    private final PostingService postingService;

    @ApiOperation(value = "게시물 생성", notes = "로그인 된 유저를 판매자로 설정하며 게시물을 생성한다.")
    @PostMapping("/posting")
    public Success newPosting(@ModelAttribute PostingForm form,
                              @SessionAttribute(SessionConst.LOGIN_MEMBER) Long memberId) {

        // 파일 저장 및 posting 생성 후 저장
        postingService.newPosting(form, memberId);

        return new Success(true);
    }

    @ApiOperation(value = "게시물 조회", notes = "로그인 된 유저의 주소를 조건으로 게시물을 기본 10개씩 페이징 해온다.(파라미터로 개수 설정 가능)")
    @GetMapping("/posting")//디폴트 페이징은 로그인 된 회원의 city 로 찾고 createdDate 로 정렬하여 페이징한다.
    public Slice<PostingResponse> findPostingByPaging(@SessionAttribute(SessionConst.LOGIN_MEMBER) Long memberId,
                                                      @PageableDefault(sort = "createdDate") Pageable pageable) {

        return postingService.findPagingPosting(memberId, pageable);
    }

    @ApiOperation(value = "게시물 상세정보 조회", notes = "게시물의 ID를 받아서 게시물의 상세정보를 조회한다.")
    @GetMapping("/posting/{postingId}")
    public PostingDetailResponse findPostingDetail(@PathVariable Long postingId,
                                                   @SessionAttribute(SessionConst.LOGIN_MEMBER) Long memberId) {

        return postingService.findDetailPosting(postingId, memberId);
    }

    @ApiOperation(value = "게시물 구매완료 상태로 변경", notes = "구매자 ID와 게시물 ID를 받아 해당 게시물의 구매자를 설정후 구매완료 상태로 변경한다.")
    @PatchMapping("/posting/") //게시물 상태 구매완료로 변경
    public Success buy(@RequestParam Long memberId,
                       @RequestParam Long postingId) {

        postingService.buy(memberId, postingId);

        return new Success(true);
    }

    @ApiOperation(value = "판매목록 조회", notes = "로그인 된 회원이 판매중이거나 판매완료 된 게시물을 조회한다.")
    @GetMapping("/purchase_log") //판매목록 조회
    public BasicResponse<List<PostingResponse>> findPurchaseLogs(@SessionAttribute(SessionConst.LOGIN_MEMBER) Long memberId) {

        List<Posting> postings = postingRepository.findByBuyer(memberId);

        List<PostingResponse> result = postings.stream().map((PostingResponse::new))
                .collect(Collectors.toList());

        return new BasicResponse<>(result);
    }

    @ApiOperation(value = "구매목록 조회", notes = "로그인 된 회원이 구매한 게시물을 조회한다.")
    @GetMapping("/sale_log") //구매목록 조회
    public BasicResponse<List<PostingResponse>> findSaleLogs(@SessionAttribute(SessionConst.LOGIN_MEMBER) Long memberId) {

        List<Posting> postings = postingRepository.findBySeller(memberId);

        List<PostingResponse> result = postings.stream().map(PostingResponse::new)
                .collect(Collectors.toList());

        return new BasicResponse<>(result);
    }
}
