package team1.Daangn_Clonecoding.domain.member.memberrepository;

import org.springframework.data.jpa.repository.JpaRepository;
import team1.Daangn_Clonecoding.domain.member.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {


}
