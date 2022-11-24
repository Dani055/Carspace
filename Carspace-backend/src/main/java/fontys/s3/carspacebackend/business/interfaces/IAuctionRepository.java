package fontys.s3.carspacebackend.business.interfaces;

import fontys.s3.carspacebackend.domain.Auction;
import fontys.s3.carspacebackend.domain.AuctionFilters;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IAuctionRepository {
    Long saveAuction(Auction auction, Long userId, List<String> urls);
    List<Auction> getAuctionsByCreator(long creatorId);
    Auction getAuctionById(Long aucId);
    Long changeAuctionInfo(Auction auction, List<String> urls);
    boolean deleteAuction(Long auctionId);
    Page<Auction> findLiveAuctionsByFilters(AuctionFilters filters, Pageable pageable);
    public void endAuctions();
}
