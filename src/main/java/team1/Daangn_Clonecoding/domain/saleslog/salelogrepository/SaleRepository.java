package team1.Daangn_Clonecoding.domain.saleslog.salelogrepository;

import org.springframework.data.jpa.repository.JpaRepository;
import team1.Daangn_Clonecoding.domain.saleslog.SalesLog;

public interface SaleRepository extends JpaRepository<SalesLog, Long> {

}
