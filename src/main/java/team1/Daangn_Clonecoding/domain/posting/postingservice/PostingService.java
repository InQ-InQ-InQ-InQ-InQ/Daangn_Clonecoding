package team1.Daangn_Clonecoding.domain.posting.postingservice;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team1.Daangn_Clonecoding.domain.file.FileStore;
import team1.Daangn_Clonecoding.domain.file.UploadFile;
import team1.Daangn_Clonecoding.domain.member.Evaluation;
import team1.Daangn_Clonecoding.domain.member.Member;
import team1.Daangn_Clonecoding.domain.member.memberrepository.MemberRepository;
import team1.Daangn_Clonecoding.domain.posting.Posting;
import team1.Daangn_Clonecoding.domain.posting.PostingType;
import team1.Daangn_Clonecoding.domain.posting.dto.PostingRequest;
import team1.Daangn_Clonecoding.domain.posting.postingrepository.PostingRepository;
import team1.Daangn_Clonecoding.web.exception.AlreadyExistBuyerException;
import team1.Daangn_Clonecoding.web.exception.NotExistPkException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostingService {

    private final PostingRepository postingRepository;
    private final FileStore fileStore;
    private final MemberRepository memberRepository;

    @Transactional // newPosting
    public Long newPosting(PostingRequest form, Long memberId) {
        //파일 저장
        List<UploadFile> uploadFiles = fileStore.storeFiles(form.getMultipartFiles());

        //회원정보 조회
        Member member = findMemberById(memberId);

        //게시물 생성
        Posting posting = Posting.createPosting(form.getTitle(), form.getCategory(),
                form.getProductPrice(), form.getExplains(), uploadFiles, member);

        //게시물 저장
        postingRepository.save(posting);

        return posting.getId();
    }

    // findPostingByPaging
    public Slice<Posting> findPagingPosting(Long memberId, Pageable pageable) {

        //city 추출
        Member seller = findMemberById(memberId);
        String city = seller.getAddress().getCity();

        //페이징하여 데이터 조회
        Slice<Posting> paging = postingRepository.findByCity(city, pageable);

        // 한 트렌젝션에서 해결하기 위해 강제 호출
        paging.getContent().get(0).getUploadFileEntities();

        return paging;
    }

    // findPostingDetail
    public Posting findDetailPosting(Long postingId) {

        //posting 조회
        Posting posting = findPostingWithSellerById(postingId);

        // 한 트렌젝션에서 해결하기 위해 강제 호출
        posting.getUploadFileEntities();

        //Dto 로 변환 후 반환
        return posting;
    }

    @Transactional //buy, 구매완료 변경 시 PostingType 변경과 Buyer 설정
    public void buy(Long memberId, Long postingId) {

        Member member = findMemberById(memberId);
        Posting posting = findPostingWithBuyerById(postingId);

        if (posting.getBuyer() != null) {
            throw new AlreadyExistBuyerException("구매자가 이미 존재하는 게시물 입니다.");
        }

        posting.addBuyer(member);
        posting.changePostingType(PostingType.FIN);
    }

    @Transactional
    public void evaluateBuyer(Long postingId, Evaluation evaluation) {

        Posting posting = findPostingWithBuyerById(postingId);
        Member buyer = posting.getBuyer();

        switch (evaluation) {
            case VERY_BAD:
                buyer.lowerMTemp(0.2);
                break;
            case BAD:
                buyer.lowerMTemp(0.1);
                break;
            case GOOD:
                buyer.raiseMTemp(0.1);
                break;
            case VERY_GOOD:
                buyer.raiseMTemp(0.2);
                break;
        }
    }
//------------------------------------로컬 메서드----------------------------------------------
    //id로 posting 조회  (seller 패치조인)
    private Posting findPostingWithSellerById(Long id) {
        return postingRepository.findWithSellerById(id).orElseThrow(() -> new NotExistPkException("존재하지 않는 pk 입니다."));
    }

    //id로 posting 조회 (buyer 패치조인)
    private Posting findPostingWithBuyerById(Long id) {
        return postingRepository.findWithBuyerById(id).orElseThrow(() -> new NotExistPkException("존재하지 않는 pk 입니다."));
    }

    //id로 member 조회
    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new NotExistPkException("존재하지 않는 pk 입니다."));
    }
}
