package team1.Daangn_Clonecoding.web.posting;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import team1.Daangn_Clonecoding.domain.file.FileStore;
import team1.Daangn_Clonecoding.domain.file.UploadFile;
import team1.Daangn_Clonecoding.domain.member.Member;
import team1.Daangn_Clonecoding.domain.member.memberrepository.MemberRepository;
import team1.Daangn_Clonecoding.domain.posting.Posting;
import team1.Daangn_Clonecoding.domain.posting.postingrepository.PostingRepository;
import team1.Daangn_Clonecoding.web.SessionConst;
import team1.Daangn_Clonecoding.web.exception.NotExistPkException;
import team1.Daangn_Clonecoding.web.posting.dto.PostingDetailResponse;
import team1.Daangn_Clonecoding.web.posting.dto.PostingForm;
import team1.Daangn_Clonecoding.web.posting.dto.PostingResponse;
import team1.Daangn_Clonecoding.web.response.Success;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posting")
public class PostingController {

    private final PostingRepository postingRepository;
    private final FileStore fileStore;
    private final MemberRepository memberRepository;

    @PostMapping("/new")
    public Success newPosting(@ModelAttribute PostingForm form,
                              @SessionAttribute(SessionConst.LOGIN_MEMBER) Long memberId) {

        //파일 저장
        List<UploadFile> uploadFiles = fileStore.storeFiles(form.getMultipartFiles());

        //회원정보 조회
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NotExistPkException("존재하지 않는 pk 입니다."));

        //게시물 생성
        Posting posting = Posting.createPosting(memberId, form.getTitle(), form.getCategory(),
                form.getProductPrice(), form.getExplains(), uploadFiles, member.getAddress().getCity());

        //게시물 저장
        postingRepository.save(posting);

        return new Success(true);
    }

    @GetMapping//디폴트 페이징은 로그인 된 회원의 city 로 찾고 createdDate 로 정렬하여 페이징한다.
    public Slice<PostingResponse> findPostingByPaging(@SessionAttribute(SessionConst.LOGIN_MEMBER) Long memberId,
                                                      @PageableDefault(sort = "createdDate") Pageable pageable) {

        //city 추출
        Member admin = memberRepository.findById(memberId).orElseThrow(() -> new NotExistPkException("존재하지 않는 pk 입니다."));
        String city = admin.getAddress().getCity();

        //페이징하여 데이터 조회
        Slice<Posting> paging = postingRepository.findByCity(city, pageable);

        //조회한 Posting 들에서 AdminId 추출
        List<Long> adminIds = paging.getContent().stream()
                .map(Posting::getAdminId).collect(Collectors.toList());

        //AdminId 로 member 조회 --> 나중에 PostingResponse 로 변환할 때 각각 쿼리를 칠때 쿼리를 통합하기 위한 쿼리
        if (!adminIds.isEmpty()) {
            memberRepository.findMembersByAdminIds(adminIds);
        }

        //Dto 로 변환후 반환
        return paging.map(posting -> {
            Member seller = memberRepository.findById(posting.getAdminId())
                    .orElseThrow(() -> new NotExistPkException("존재하지 않는 pk 입니다.")); // 판매자 조회
            return new PostingResponse(posting, seller);
        });
    }

    @GetMapping("{id}")
    public PostingDetailResponse findPostingDetail(@PathVariable Long id,
                                                   @SessionAttribute(SessionConst.LOGIN_MEMBER) Long memberId) {

        //posting 조회
        Posting posting = postingRepository.findById(id).orElseThrow(() -> new NotExistPkException("존재하지 않는 pk 입니다."));

        //판매자 조회
        Member seller = memberRepository.findById(posting.getAdminId())
                .orElseThrow(() -> new NotExistPkException("존재하지 않는 pk 입니다."));

        //Dto 로 변환 후 반환
        return new PostingDetailResponse(posting, memberId, seller);
    }
}
