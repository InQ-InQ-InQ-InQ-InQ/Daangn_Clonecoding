package team1.Daangn_Clonecoding.web.member.dto;

import lombok.Data;
import team1.Daangn_Clonecoding.domain.member.Address;
import team1.Daangn_Clonecoding.domain.member.Member;

@Data
public class MemberDetailResponse {
    private String nickname;
    private Address address;
    private Double mTemp;

    public MemberDetailResponse(Member member) {
        this.nickname = member.getNickname();
        this.address = member.getAddress();
        this.mTemp = member.getMTemp();
    }
}
