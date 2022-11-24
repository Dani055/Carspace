package fontys.s3.carspacebackend.persistence.repository;

import fontys.s3.carspacebackend.persistence.Entity.BidEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IJPABidRepository extends JpaRepository<BidEntity, Long> {

}
