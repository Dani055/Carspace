package fontys.s3.carspacebackend.persistence.repository;
import fontys.s3.carspacebackend.persistence.entity.BidEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IJPABidRepository extends JpaRepository<BidEntity, Long> {
    @EntityGraph(
            type = EntityGraph.EntityGraphType.LOAD,
            attributePaths = {
                    "bidder"
            }
    )
    List<BidEntity> findByAuctionHasSoldOrderByAmountDesc(boolean hasSold);
}
