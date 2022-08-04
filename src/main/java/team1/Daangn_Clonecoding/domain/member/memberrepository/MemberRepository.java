package team1.Daangn_Clonecoding.domain.member.memberrepository;

import org.springframework.data.jpa.repository.JpaRepository;
import team1.Daangn_Clonecoding.domain.member.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByNickname(String nickname);

    Optional<Member> findByLoginId(String loginId);
}
