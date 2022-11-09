package fontys.s3.carspacebackend.persistence.repository;

import fontys.s3.carspacebackend.persistence.Entity.AuctionEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IJPAAuctionRepository extends JpaRepository<AuctionEntity, Long> {

    @EntityGraph(
            type = EntityGraph.EntityGraphType.LOAD,
            attributePaths = {
                    "creator",
                    "images",
                    "comments",
                    "bids",
                    "comments.creator",
                    "bids.bidder",
            }
    )

    Optional<AuctionEntity> findById(Long id);

    @EntityGraph(
            type = EntityGraph.EntityGraphType.LOAD,
            attributePaths = {
                    "creator",
                    "images",
                    "comments",
                    "bids",
                    "comments.creator",
                    "bids.bidder",
            }
    )
    List<AuctionEntity> findAll();
}
