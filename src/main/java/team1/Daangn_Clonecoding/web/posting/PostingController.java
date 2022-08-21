package team1.Daangn_Clonecoding.web.posting;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import team1.Daangn_Clonecoding.domain.file.FileStore;
import team1.Daangn_Clonecoding.domain.file.UploadFile;
import team1.Daangn_Clonecoding.domain.member.Member;
import team1.Daangn_Clonecoding.domain.member.memberrepository.MemberRepository;
import team1.Daangn_Clonecoding.domain.posting.Posting;
import team1.Daangn_Clonecoding.domain.posting.postingrepository.PostingRepository;
import team1.Daangn_Clonecoding.domain.purchaselog.PurchaseLog;
import team1.Daangn_Clonecoding.domain.purchaselog.purchaselogrepository.PurchaseRepository;
import team1.Daangn_Clonecoding.web.SessionConst;
import team1.Daangn_Clonecoding.web.exception.NotExistPkException;
import team1.Daangn_Clonecoding.web.posting.dto.PostingForm;
import team1.Daangn_Clonecoding.web.response.Success;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posting")
public class PostingController {

    private final PostingRepository postingRepository;
    private final FileStore fileStore;
    private final PurchaseRepository purchaseRepository;
    private final MemberRepository memberRepository;

    @PostMapping("/new")
    public Success newPosting(@ModelAttribute PostingForm form,
                              @SessionAttribute(value = SessionConst.LOGIN_MEMBER, required = false) Long memberId) {

        //파일 저장
        List<UploadFile> uploadFiles = fileStore.storeFiles(form.getMultipartFiles());

        //게시물 생성
        Posting posting = Posting.createPosting(form.getTitle(), form.getCategory(), form.getProductName(),
                form.getProductPrice(), form.getExplains(), uploadFiles);

        //게시물 저장
        postingRepository.save(posting);

        //판매목록 생성
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NotExistPkException("존재하지 않는 pk 입니다."));
        PurchaseLog purchaseLog = PurchaseLog.createPurchaseLog(member, posting);

        //판매목록 저장
        purchaseRepository.save(purchaseLog);

        return new Success(true);
    }
}
