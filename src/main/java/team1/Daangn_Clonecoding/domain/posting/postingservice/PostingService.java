package team1.Daangn_Clonecoding.domain.posting.postingservice;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team1.Daangn_Clonecoding.domain.file.FileStore;
import team1.Daangn_Clonecoding.domain.file.UploadFile;
import team1.Daangn_Clonecoding.domain.member.Member;
import team1.Daangn_Clonecoding.domain.member.memberrepository.MemberRepository;
import team1.Daangn_Clonecoding.domain.posting.Posting;
import team1.Daangn_Clonecoding.domain.posting.dto.PostingDetailResponse;
import team1.Daangn_Clonecoding.domain.posting.dto.PostingForm;
import team1.Daangn_Clonecoding.domain.posting.dto.PostingResponse;
import team1.Daangn_Clonecoding.domain.posting.postingrepository.PostingRepository;
import team1.Daangn_Clonecoding.web.exception.NotExistPkException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostingService {

    private final PostingRepository postingRepository;
    private final FileStore fileStore;
    private final MemberRepository memberRepository;

    @Transactional // newPosting
    public void newPosting(PostingForm form, Long memberId) {
        //파일 저장
        List<UploadFile> uploadFiles = fileStore.storeFiles(form.getMultipartFiles());

        //회원정보 조회
        Member member = findMemberById(memberId);

        //게시물 생성
        Posting posting = Posting.createPosting(form.getTitle(), form.getCategory(),
                form.getProductPrice(), form.getExplains(), uploadFiles, member);

        //게시물 저장
        postingRepository.save(posting);
    }

    // findPostingByPaging
    public Slice<PostingResponse> findPagingPosting(Long memberId, Pageable pageable) {

        //city 추출
        Member seller = findMemberById(memberId);
        String city = seller.getAddress().getCity();

        //페이징하여 데이터 조회
        Slice<Posting> paging = postingRepository.findByCity(city, pageable);

        return paging.map(PostingResponse::new);
    }

    // findPostingDetail
    public PostingDetailResponse findDetailPosting(Long postingId, Long memberId) {

        //posting 조회
        Posting posting = findPostingById(postingId);

        //Dto 로 변환 후 반환
        return new PostingDetailResponse(posting, memberId);
    }

    //id로 posting 조회
    private Posting findPostingById(Long id) {
        return postingRepository.findById(id).orElseThrow(() -> new NotExistPkException("존재하지 않는 pk 입니다."));
    }

    //id로 member 조회
    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new NotExistPkException("존재하지 않는 pk 입니다."));
    }
}
