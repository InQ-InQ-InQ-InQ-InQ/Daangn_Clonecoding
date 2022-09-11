package team1.Daangn_Clonecoding.domain.purchaserequset.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team1.Daangn_Clonecoding.domain.member.Member;
import team1.Daangn_Clonecoding.domain.member.memberrepository.MemberRepository;
import team1.Daangn_Clonecoding.domain.posting.Posting;
import team1.Daangn_Clonecoding.domain.posting.postingrepository.PostingRepository;
import team1.Daangn_Clonecoding.domain.purchaserequset.PurchaseRequest;
import team1.Daangn_Clonecoding.domain.purchaserequset.dto.PrRequset;
import team1.Daangn_Clonecoding.domain.purchaserequset.purchaserequestrepository.PurchaseRequestRepository;
import team1.Daangn_Clonecoding.web.exception.NotExistPkException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PurchaseRequestService {

    private final MemberRepository memberRepository;
    private final PostingRepository postingRepository;
    private final PurchaseRequestRepository purchaseRequestRepository;

    @Transactional
    public Long newPurchaseRequest(PrRequset form, Long memberId) {

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NotExistPkException("존재하지 않는 pk 입니다."));
        Posting posting = postingRepository.findWithSellerById(form.getPostingId()).orElseThrow(() -> new NotExistPkException("존재하지 않는 pk 입니다."));

        PurchaseRequest purchaseRequest = PurchaseRequest.createPurchaseRequest(member, posting, form.getMessage());

        purchaseRequestRepository.save(purchaseRequest);

        return purchaseRequest.getId();
    }
}
