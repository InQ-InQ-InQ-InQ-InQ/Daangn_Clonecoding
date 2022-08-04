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

    @Column(nullable = false, unique = true)
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

    public Member(String name, String nickname, String loginId, String loginPw, Address address) {
        this.name = name;
        this.nickname = nickname;
        this.loginId = loginId;
        this.loginPw = loginPw;
        this.address = address;
    }
}
