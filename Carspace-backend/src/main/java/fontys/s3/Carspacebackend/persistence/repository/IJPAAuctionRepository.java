package fontys.s3.Carspacebackend.persistence.repository;

import fontys.s3.Carspacebackend.persistence.Entity.AuctionEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

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
                    "bids.bidder",
                    "comments.creator"
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
                    "bids.bidder",
                    "comments.creator"
            }
    )
    List<AuctionEntity> findAll();
}
