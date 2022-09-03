package team1.Daangn_Clonecoding.domain.purchaserequset;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team1.Daangn_Clonecoding.domain.auditing.BaseEntity;
import team1.Daangn_Clonecoding.domain.member.Member;
import team1.Daangn_Clonecoding.domain.posting.Posting;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PurchaseRequest extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id", nullable = false)
    private Member buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posting_id", nullable = false)
    private Posting posting;

    private String message;

    //편의 메서드
    private PurchaseRequest(Member buyer, Posting posting, String message) {
        this.buyer = buyer;
        this.posting = posting;
        this.message = message;
    }

    //생성 메서드
    public static PurchaseRequest createPurchaseRequest(Member member, Posting posting, String message) {
        return new PurchaseRequest(member, posting, message);
    }
}
