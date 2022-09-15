package fontys.s3.Carspacebackend.business.service;

import fontys.s3.Carspacebackend.domain.requests.CreateAuctionReq;
import fontys.s3.Carspacebackend.persistence.Entity.AuctionEntity;

public interface IAuctionService {
    AuctionEntity createAuction(CreateAuctionReq req);
}
