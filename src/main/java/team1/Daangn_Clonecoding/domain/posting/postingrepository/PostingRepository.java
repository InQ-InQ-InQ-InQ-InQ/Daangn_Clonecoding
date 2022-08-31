package team1.Daangn_Clonecoding.domain.posting.postingrepository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import team1.Daangn_Clonecoding.domain.posting.Posting;

import java.util.List;

public interface PostingRepository extends JpaRepository<Posting, Long> {

    Slice<Posting> findDistinctByCity(String city, Pageable pageable);
}
