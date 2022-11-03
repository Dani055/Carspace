package fontys.s3.Carspacebackend.business.interfaces;

import fontys.s3.Carspacebackend.domain.Auction;

import java.util.List;

public interface IAuctionRepository {
    Long saveAuction(Auction auction, Long userId, List<String> urls);
    List<Auction> getAuctions();

    Auction getAuctionById(Long aucId);
    Long changeAuctionInfo(Auction auction, List<String> urls);
    boolean deleteAuction(Long auctionId);
}
