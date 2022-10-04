package fontys.s3.Carspacebackend.business.service;

import fontys.s3.Carspacebackend.domain.Auction;
import fontys.s3.Carspacebackend.domain.requests.CreateAuctionReq;
import fontys.s3.Carspacebackend.persistence.Entity.AuctionEntity;

import java.util.List;

public interface IAuctionService {
    Long createAuction(Auction auc, Long userId, List<String> urls);
    List<Auction> getAuctions();

    Auction getAuctionDetails(Long id);
}
