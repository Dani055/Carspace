package fontys.s3.carspacebackend.business.service;

import fontys.s3.carspacebackend.domain.Auction;
import fontys.s3.carspacebackend.domain.AuctionFilters;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IAuctionService {
    Long createAuction(Auction auc, List<String> urls);
    List<Auction> getAuctions();

    Auction getAuctionDetails(Long id);
    Long editAuction(Auction auc, List<String> urls);

    boolean deleteAuction(Long auctionId);
     Page<Auction> getLiveAuctions(AuctionFilters filters, Pageable pageable);
}
