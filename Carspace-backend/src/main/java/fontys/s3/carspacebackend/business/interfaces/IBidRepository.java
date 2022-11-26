package fontys.s3.carspacebackend.business.interfaces;

import fontys.s3.carspacebackend.domain.Bid;

import java.util.List;


public interface IBidRepository {
    Long saveBid(Bid bid, Long auctionId, Long userId);
    List<Bid> getBidsOnLiveAuctions();
}
