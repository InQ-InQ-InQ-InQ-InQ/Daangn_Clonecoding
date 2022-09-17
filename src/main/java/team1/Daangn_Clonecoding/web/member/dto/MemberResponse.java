package team1.Daangn_Clonecoding.web.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import team1.Daangn_Clonecoding.domain.member.Member;

@Data
public class MemberResponse {
    private String nickname;
    private String city;

    public MemberResponse(Member member) {
        this.nickname = member.getNickname();
        this.city = member.getAddress().getCity();
    }
}
