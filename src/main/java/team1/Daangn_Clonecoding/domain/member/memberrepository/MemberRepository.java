package team1.Daangn_Clonecoding.domain.member.memberrepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team1.Daangn_Clonecoding.domain.member.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByNickname(String nickname);

    Optional<Member> findByLoginId(String loginId);

    Optional<Member> findByPhoneNumber(String phoneNumber);

    @Query("select m from Member m where m.id in :adminIds")
    List<Member> findMembersByAdminIds(List<Long> adminIds);
}
