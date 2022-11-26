package fontys.s3.carspacebackend.business.service;

import fontys.s3.carspacebackend.domain.Bid;

import java.util.List;

public interface IBidService {
    Long createBid(Bid b, Long auctionId);
    List<Bid> getAllBidsOnLiveAuctions();
}
