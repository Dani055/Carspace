package fontys.s3.Carspacebackend.persistence.repository;

import fontys.s3.Carspacebackend.persistence.Entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAuctionImageRepository extends JpaRepository<ImageEntity, Long> {
}
