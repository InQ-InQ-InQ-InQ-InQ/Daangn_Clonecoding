package team1.Daangn_Clonecoding.domain.posting.postingrepository;

import org.springframework.data.jpa.repository.JpaRepository;
import team1.Daangn_Clonecoding.domain.posting.Posting;

public interface PostingRepository extends JpaRepository<Posting, Long> {

}
