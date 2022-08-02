package team1.Daangn_Clonecoding.domain.member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String loginId;

    @Column(nullable = false)
    private String loginPw;

    @Embedded
    private Address address;

    @Column(nullable = false)
    private Double mTemp = 36.5;
}
