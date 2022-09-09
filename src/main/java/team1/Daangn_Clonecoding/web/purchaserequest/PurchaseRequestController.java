package team1.Daangn_Clonecoding.web.purchaserequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team1.Daangn_Clonecoding.domain.member.Member;
import team1.Daangn_Clonecoding.domain.member.memberrepository.MemberRepository;
import team1.Daangn_Clonecoding.domain.posting.Posting;
import team1.Daangn_Clonecoding.domain.posting.postingrepository.PostingRepository;
import team1.Daangn_Clonecoding.domain.purchaserequset.PurchaseRequest;
import team1.Daangn_Clonecoding.domain.purchaserequset.purchaserequestrepository.PurchaseRequestRepository;
import team1.Daangn_Clonecoding.domain.purchaserequset.service.PurchaseRequestService;
import team1.Daangn_Clonecoding.web.SessionConst;
import team1.Daangn_Clonecoding.web.exception.NotExistPkException;
import team1.Daangn_Clonecoding.domain.purchaserequset.dto.PrRequset;
import team1.Daangn_Clonecoding.web.purchaserequest.dto.SimplePrResponse;
import team1.Daangn_Clonecoding.web.response.Success;

@RestController
@RequiredArgsConstructor
@RequestMapping("purchase_request")
public class PurchaseRequestController {

    private final PurchaseRequestService prService;

    @PostMapping
    @Operation(summary = "구매요청 생성", description = "게시물 ID와 메세지를 입력받아 구매요청을 생성한다.")
    public ResponseEntity<SimplePrResponse> newPurchaseRequest(@Parameter(description = "세션에서 가져오는 데이터로 값 입력 X")
                                                                   @SessionAttribute(SessionConst.LOGIN_MEMBER) Long memberId,
                                                               @ModelAttribute PrRequset form) {
        Long prId = prService.newPurchaseRequest(form, memberId);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Location", "/api/purchase_request/" + prId);

        return new ResponseEntity<>(new SimplePrResponse(prId), headers, HttpStatus.CREATED);
    }
}
