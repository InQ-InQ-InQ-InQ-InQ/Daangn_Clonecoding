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

    //페이징을 위해 uploadFileEntities 패치조인 사용 X
    @Query("select p from Posting p join fetch p.seller m where m.address.city = :city")
    Slice<Posting> findByCity(String city, Pageable pageable);

    //seller 패치조인 findById
    @EntityGraph(attributePaths = "seller")
    Optional<Posting> findWithSellerById(Long id);

    //buyer 패치조인 findById
    @EntityGraph(attributePaths = "seller")
    Optional<Posting> findWithBuyerById(Long id);

    //buyer, seller 패치조인 findByBuyer
    @EntityGraph(attributePaths = {"buyer", "seller"})
    @Query("select p from Posting p where p.buyer.id = :memberId")
    List<Posting> findByBuyer(Long memberId);

    //seller 패치조인 findBySeller
    @EntityGraph(attributePaths = "seller")
    @Query("select p from Posting p where p.seller.id = :memberId")
    List<Posting> findBySeller(Long memberId);
}
