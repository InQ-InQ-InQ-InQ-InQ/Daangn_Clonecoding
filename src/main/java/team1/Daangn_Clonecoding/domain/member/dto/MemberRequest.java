package team1.Daangn_Clonecoding.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import team1.Daangn_Clonecoding.domain.member.Address;

@Data
@AllArgsConstructor
public class MemberRequest {

    private String name;

    private String nickname;

    private String phoneNumber;

    private String loginId;

    private String loginPw;

    private String city;

    private String town;
}
