package team1.Daangn_Clonecoding.domain.purchaserequset;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team1.Daangn_Clonecoding.domain.member.Member;
import team1.Daangn_Clonecoding.domain.posting.Posting;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PurchaseRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "posting_id", nullable = false)
    private Posting posting;

    //편의 메서드
    private PurchaseRequest(Member member, Posting posting) {
        this.member = member;
        this.posting = posting;
    }

    //생성 메서드
    public static PurchaseRequest createPurchaseRequest(Member member, Posting posting) {
        return new PurchaseRequest(member, posting);
    }
}
