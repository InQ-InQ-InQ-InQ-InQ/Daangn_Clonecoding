package team1.Daangn_Clonecoding.domain.member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team1.Daangn_Clonecoding.domain.auditing.BaseEntity;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false, unique = true)
    private String loginId;

    @Column(nullable = false)
    private String loginPw;

    @Embedded
    private Address address;

    @Column(nullable = false)
    private Double mTemp = 36.5;

    //편의 메서드
    public void changeTemp(Double t) {
        mTemp = t;
    }

    private Member(String name, String nickname, String phoneNumber, String loginId, String loginPw, Address address) {
        this.name = name;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.loginId = loginId;
        this.loginPw = loginPw;
        this.address = address;
    }

    //생성 메서드
    public static Member createMember(String name, String nickname, String phoneNumber, String loginId,String loginPw, Address address) {
        return new Member(name, nickname, phoneNumber, loginId, loginPw, address);
    }
}
