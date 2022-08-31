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
import team1.Daangn_Clonecoding.web.posting.dto.PostingForm;
import team1.Daangn_Clonecoding.web.posting.dto.PostingResponse;
import team1.Daangn_Clonecoding.web.response.Success;

import java.util.List;

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
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NotExistPkException("존재하지 않는 pk 입니다."));
        String city = member.getAddress().getCity();

        //페이징하여 데이터 조회
        Slice<Posting> paging = postingRepository.findDistinctByCity(city, pageable);

        //Dto 로 변환후 반환
        return paging.map(PostingResponse::new);
    }
}
