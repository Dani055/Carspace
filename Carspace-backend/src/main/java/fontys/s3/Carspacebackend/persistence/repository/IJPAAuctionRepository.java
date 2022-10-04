package fontys.s3.Carspacebackend.persistence.repository;

import fontys.s3.Carspacebackend.persistence.Entity.AuctionEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IJPAAuctionRepository extends JpaRepository<AuctionEntity, Long> {

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {
                    "creator",
                    "creator.role",
                    "images",
                    "comments",
                    "bids",
            }
    )
    Optional<AuctionEntity> findById(Long id);

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {
                    "creator",
                    "creator.role",
                    "images",
                    "comments",
                    "bids",
            }
    )
    List<AuctionEntity> findAll();
}
