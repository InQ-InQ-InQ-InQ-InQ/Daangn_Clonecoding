package team1.Daangn_Clonecoding.domain.posting.postingrepository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team1.Daangn_Clonecoding.domain.posting.Posting;

import java.util.List;
import java.util.Optional;

public interface PostingRepository extends JpaRepository<Posting, Long> {

    //페이징을 위해 uploadFileEntities fetch join 사용 X
    @Query("select p from Posting p join fetch p.seller m where m.address.city = :city")
    Slice<Posting> findByCity(String city, Pageable pageable);

    //기본 findById 자동 join fetch p.seller
    @Override
    @EntityGraph(attributePaths = "seller")
    Optional<Posting> findById(Long aLong);
}
