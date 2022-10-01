package fontys.s3.Carspacebackend.business.interfaces;

import fontys.s3.Carspacebackend.domain.Auction;

public interface IAuctionRepository {
    Long saveAuction(Auction auction, Long userId);
}
