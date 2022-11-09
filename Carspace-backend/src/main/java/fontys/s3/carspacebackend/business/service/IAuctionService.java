package fontys.s3.carspacebackend.business.service;

import fontys.s3.carspacebackend.domain.Auction;

import java.util.List;

public interface IAuctionService {
    Long createAuction(Auction auc, List<String> urls);
    List<Auction> getAuctions();

    Auction getAuctionDetails(Long id);
    Long editAuction(Auction auc, List<String> urls);

    boolean deleteAuction(Long auctionId);
}
