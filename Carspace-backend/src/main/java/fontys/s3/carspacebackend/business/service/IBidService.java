package fontys.s3.carspacebackend.business.service;

import fontys.s3.carspacebackend.domain.Bid;

public interface IBidService {
    Long createBid(Bid b, Long auctionId);
}
