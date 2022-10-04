package fontys.s3.Carspacebackend.business.interfaces;

import fontys.s3.Carspacebackend.domain.Auction;
import fontys.s3.Carspacebackend.persistence.Entity.AuctionEntity;

import java.util.List;

public interface IAuctionRepository {
    Long saveAuction(Auction auction, Long userId);
    List<Auction> getAuctions();

    Auction getAuctionById(Long aucId);
}
