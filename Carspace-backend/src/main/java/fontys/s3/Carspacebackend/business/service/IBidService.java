package fontys.s3.Carspacebackend.business.service;

import fontys.s3.Carspacebackend.domain.Bid;

public interface IBidService {
    Long createBid(Bid b, Long auctionId);
}
