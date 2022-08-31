package team1.Daangn_Clonecoding.domain.saleslog;

import team1.Daangn_Clonecoding.domain.auditing.BaseEntity;
import team1.Daangn_Clonecoding.domain.member.Member;
import team1.Daangn_Clonecoding.domain.posting.Posting;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SalesLog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "posting_id", unique = true, nullable = false)
    private Posting posting;

    private SalesLog(Member member, Posting posting) {
        this.member = member;
        this.posting = posting;
    }

    //생성 메서드
    public static SalesLog createSalesLog(Member member, Posting posting) {
        return new SalesLog(member, posting);
    }
}
