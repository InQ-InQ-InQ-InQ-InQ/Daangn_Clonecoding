package team1.Daangn_Clonecoding.web.purchaserequest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import team1.Daangn_Clonecoding.domain.member.Member;
import team1.Daangn_Clonecoding.domain.member.memberrepository.MemberRepository;
import team1.Daangn_Clonecoding.domain.posting.Posting;
import team1.Daangn_Clonecoding.domain.posting.postingrepository.PostingRepository;
import team1.Daangn_Clonecoding.domain.purchaserequset.PurchaseRequest;
import team1.Daangn_Clonecoding.domain.purchaserequset.purchaserequestrepository.PurchaseRequestRepository;
import team1.Daangn_Clonecoding.web.SessionConst;
import team1.Daangn_Clonecoding.web.exception.NotExistPkException;
import team1.Daangn_Clonecoding.web.purchaserequest.dto.PrForm;
import team1.Daangn_Clonecoding.web.response.Success;

@RestController
@RequiredArgsConstructor
@RequestMapping("purchase_request")
public class PurchaseRequestController {

    private final PurchaseRequestRepository purchaseRequestRepository;
    private final MemberRepository memberRepository;
    private final PostingRepository postingRepository;

    @PostMapping("/new")
    public Success newPurchaseRequest(@ModelAttribute PrForm form,
                                      @SessionAttribute(SessionConst.LOGIN_MEMBER) Long memberId) {

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NotExistPkException("존재하지 않는 pk 입니다."));
        Posting posting = postingRepository.findWithSellerById(form.getPostingId()).orElseThrow(() -> new NotExistPkException("존재하지 않는 pk 입니다."));

        PurchaseRequest purchaseRequest = PurchaseRequest.createPurchaseRequest(member, posting, form.getMessage());

        purchaseRequestRepository.save(purchaseRequest);

        return new Success(true);
    }
}
