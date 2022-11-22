package fontys.s3.carspacebackend.persistence.repository;

import fontys.s3.carspacebackend.persistence.Entity.AuctionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
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
    List<AuctionEntity> findByCreatorId(Long creatorId);

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
    Page<AuctionEntity> findByCarBrandContainingAndCarModelContainingAndLocationContainingAndCarYearBetweenAndStartingPriceGreaterThanEqualAndBuyoutPriceLessThanEqualAndMileageBetweenAndHasSoldOrderByEndsOnAsc(String carBrand, String carModel, String location,int startYear, int endYear, double startingPrice, double buyoutPrice,int startMileage, int endMileage, boolean hasSold,Pageable pageable);

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
    Page<AuctionEntity> findByCarBrandContainingAndCarModelContainingAndLocationContainingAndCarYearBetweenAndStartingPriceGreaterThanEqualAndBuyoutPriceLessThanEqualAndMileageBetweenAndHasSoldOrderByEndsOnDesc(String carBrand, String carModel, String location,int startYear, int endYear, double startingPrice, double buyoutPrice,int startMileage, int endMileage, boolean hasSold,Pageable pageable);

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
    List<AuctionEntity> findByEndsOnBeforeAndHasSold(Instant date, boolean hasSold);
}
