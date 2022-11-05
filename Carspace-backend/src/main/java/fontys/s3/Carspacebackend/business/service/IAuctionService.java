package fontys.s3.Carspacebackend.business.service;

import fontys.s3.Carspacebackend.domain.Auction;

import java.util.List;

public interface IAuctionService {
    Long createAuction(Auction auc, List<String> urls);
    List<Auction> getAuctions();

    Auction getAuctionDetails(Long id);
    Long editAuction(Auction auc, List<String> urls);

    boolean deleteAuction(Long auctionId);
}
