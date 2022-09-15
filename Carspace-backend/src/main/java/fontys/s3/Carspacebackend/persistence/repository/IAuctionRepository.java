package fontys.s3.Carspacebackend.persistence.repository;

import fontys.s3.Carspacebackend.persistence.Entity.AuctionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAuctionRepository extends JpaRepository<AuctionEntity, Long> {
}
