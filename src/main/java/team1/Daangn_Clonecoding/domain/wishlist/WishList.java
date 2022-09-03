package team1.Daangn_Clonecoding.domain.wishlist;

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
public class WishList extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "posting_id", nullable = false)
    private Posting posting;

    private WishList(Member member, Posting posting) {
        this.member = member;
        this.posting = posting;
    }

    public static WishList createWishList(Member member, Posting posting) {
        return new WishList(member, posting);
    }
}
