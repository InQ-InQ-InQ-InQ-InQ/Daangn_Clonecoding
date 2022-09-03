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
        Posting posting = Posting.createPosting(memberId, form.getTitle(), form.getCategory(),
                form.getProductPrice(), form.getExplains(), uploadFiles, member.getAddress().getCity());

        //게시물 저장
        postingRepository.save(posting);
    }

    // findPostingByPaging
    public Slice<PostingResponse> findPagingPosting(Long memberId, Pageable pageable) {

        //city 추출
        Member admin = findMemberById(memberId);
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
            Member seller = findMemberById(posting.getAdminId());
            return new PostingResponse(posting, seller);
        });
    }

    // findPostingDetail
    public PostingDetailResponse findDetailPosting(Long postingId, Long memberId) {

        //posting 조회
        Posting posting = findPostingById(postingId);

        //판매자 조회
        Member seller = findMemberById(posting.getAdminId());

        //Dto 로 변환 후 반환
        return new PostingDetailResponse(posting, memberId, seller);
    }

    private Posting findPostingById(Long id) {
        return postingRepository.findById(id).orElseThrow(() -> new NotExistPkException("존재하지 않는 pk 입니다."));
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new NotExistPkException("존재하지 않는 pk 입니다."));
    }
}
