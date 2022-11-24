package fontys.s3.carspacebackend.persistence.repository;

import fontys.s3.carspacebackend.persistence.Entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IJPAAuctionImageRepository extends JpaRepository<ImageEntity, Long> {
}
