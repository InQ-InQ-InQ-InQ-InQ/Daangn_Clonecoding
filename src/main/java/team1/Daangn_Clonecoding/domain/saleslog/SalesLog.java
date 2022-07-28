package team1.Daangn_Clonecoding.domain.saleslog;

import team1.Daangn_Clonecoding.domain.member.Member;
import team1.Daangn_Clonecoding.domain.posting.Posting;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SalesLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "posting_id", unique = true)
    private Posting posting;
}
