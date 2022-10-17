package fontys.s3.Carspacebackend.business.interfaces;

import fontys.s3.Carspacebackend.domain.Bid;



public interface IBidRepository {
    Long saveBid(Bid bid, Long auctionId, Long userId);
}
