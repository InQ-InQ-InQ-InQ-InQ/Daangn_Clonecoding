package team1.Daangn_Clonecoding.web.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import team1.Daangn_Clonecoding.domain.member.Address;

@Data
@AllArgsConstructor
public class JoinForm {

    private String name;

    private String nickname;

    private String loginId;

    private String loginPw;

    private String city;

    private String country;

    private String district;
}
