package team1.Daangn_Clonecoding.web.posting;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team1.Daangn_Clonecoding.domain.posting.Posting;
import team1.Daangn_Clonecoding.domain.posting.postingrepository.PostingRepository;
import team1.Daangn_Clonecoding.domain.posting.postingservice.PostingService;
import team1.Daangn_Clonecoding.web.SessionConst;
import team1.Daangn_Clonecoding.domain.posting.dto.PostingDetailResponse;
import team1.Daangn_Clonecoding.domain.posting.dto.PostingForm;
import team1.Daangn_Clonecoding.domain.posting.dto.PostingResponse;
import team1.Daangn_Clonecoding.web.posting.dto.SimplePostingSuccessResponse;
import team1.Daangn_Clonecoding.web.response.CommonResponse;
import team1.Daangn_Clonecoding.web.response.Success;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostingController {

    private final PostingRepository postingRepository;
    private final PostingService postingService;

    @PostMapping("/posting")
    @Operation(summary = "게시물 생성", description = "로그인 된 유저를 판매자로 설정하며 게시물을 생성한다.")
    public ResponseEntity<SimplePostingSuccessResponse> newPosting(@Parameter(description = "세션에서 가져오는 데이터로 값 입력 X")
                                  @SessionAttribute(SessionConst.LOGIN_MEMBER) Long memberId,
                                                                   @ModelAttribute PostingForm form) {

        // 파일 저장 및 posting 생성 후 저장
        Long postingId = postingService.newPosting(form, memberId);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Location", "/api/posting/" + postingId);

        return new ResponseEntity<>(new SimplePostingSuccessResponse(postingId), headers, HttpStatus.CREATED);
    }

    @GetMapping("/posting")//디폴트 페이징은 로그인 된 회원의 city 로 찾고 createdDate 로 정렬하여 페이징한다.
    @Operation(summary = "게시물 조회", description = "로그인 된 유저의 주소를 조건으로 게시물을 기본 10개씩 페이징 해온다.(파라미터로 개수 설정 가능)")
    public Slice<PostingResponse> findPostingByPaging(@Parameter(description = "세션에서 가져오는 데이터로 값 입력 X")
                                                          @SessionAttribute(SessionConst.LOGIN_MEMBER) Long memberId,
                                                      @PageableDefault(sort = "createdDate") Pageable pageable) {

        return postingService.findPagingPosting(memberId, pageable);
    }

    @GetMapping("/posting/{postingId}")
    @Operation(summary = "게시물 상세정보 조회", description = "게시물의 ID를 받아서 게시물의 상세정보를 조회한다.")
    public PostingDetailResponse findPostingDetail(@Parameter(description = "세션에서 가져오는 데이터로 값 입력 X")
                                                       @SessionAttribute(SessionConst.LOGIN_MEMBER) Long memberId,
                                                   @PathVariable Long postingId) {

        return postingService.findDetailPosting(postingId, memberId);
    }

    @PatchMapping("/posting") //게시물 상태 구매완료로 변경
    @Operation(summary = "게시물 구매완료 상태로 변경", description = "구매자 ID와 게시물 ID를 받아 해당 게시물의 구매자를 설정후 구매완료 상태로 변경한다.")
    public ResponseEntity<Void> buy(@RequestParam Long memberId,
                       @RequestParam Long postingId) {

        postingService.buy(memberId, postingId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/purchase_log") //판매목록 조회
    @Operation(summary = "판매목록 조회", description = "로그인 된 회원이 판매중이거나 판매완료 된 게시물을 조회한다.")
    public CommonResponse<List<PostingResponse>> findPurchaseLogs(@Parameter(description = "세션에서 가져오는 데이터로 값 입력 X")
                                                                     @SessionAttribute(SessionConst.LOGIN_MEMBER) Long memberId) {

        List<Posting> postings = postingRepository.findByBuyer(memberId);

        List<PostingResponse> result = postings.stream().map((PostingResponse::new))
                .collect(Collectors.toList());

        return new CommonResponse<>(result);
    }

    @GetMapping("/sale_log") //구매목록 조회
    @Operation(summary = "구매목록 조회", description = "로그인 된 회원이 구매한 게시물을 조회한다.")
    public CommonResponse<List<PostingResponse>> findSaleLogs(@Parameter(description = "세션에서 가져오는 데이터로 값 입력 X")
                                                                 @SessionAttribute(SessionConst.LOGIN_MEMBER) Long memberId) {

        List<Posting> postings = postingRepository.findBySeller(memberId);

        List<PostingResponse> result = postings.stream().map(PostingResponse::new)
                .collect(Collectors.toList());

        return new CommonResponse<>(result);
    }
}
