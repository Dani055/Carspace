package fontys.s3.carspacebackend.business.interfaces;

import fontys.s3.carspacebackend.domain.Bid;



public interface IBidRepository {
    Long saveBid(Bid bid, Long auctionId, Long userId);
}
