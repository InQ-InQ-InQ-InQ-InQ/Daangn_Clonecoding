package team1.Daangn_Clonecoding.domain.purchaselog.purchaselogrepository;

import org.springframework.data.jpa.repository.JpaRepository;
import team1.Daangn_Clonecoding.domain.purchaselog.PurchaseLog;

public interface PurchaseRepository extends JpaRepository<PurchaseLog, Long> {

}
