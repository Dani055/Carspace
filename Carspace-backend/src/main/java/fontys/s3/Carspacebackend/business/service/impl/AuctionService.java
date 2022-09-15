package fontys.s3.Carspacebackend.business.service.impl;

import fontys.s3.Carspacebackend.business.service.IAuctionService;
import fontys.s3.Carspacebackend.domain.requests.CreateAuctionReq;
import fontys.s3.Carspacebackend.persistence.Entity.AuctionEntity;
import fontys.s3.Carspacebackend.persistence.Entity.ImageEntity;
import fontys.s3.Carspacebackend.persistence.Entity.UserEntity;
import fontys.s3.Carspacebackend.persistence.repository.IAuctionImageRepository;
import fontys.s3.Carspacebackend.persistence.repository.IAuctionRepository;
import fontys.s3.Carspacebackend.persistence.repository.IRoleRepository;
import fontys.s3.Carspacebackend.persistence.repository.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuctionService implements IAuctionService {
    private IUserRepository userRepository;

    private IAuctionRepository auctionRepository;

    private IAuctionImageRepository auctionImageRepository;

    public AuctionService(IUserRepository userRepository, IAuctionRepository auctionRepository, IAuctionImageRepository auctionImageRepository){
        this.userRepository = userRepository;
        this.auctionRepository = auctionRepository;
        this.auctionImageRepository = auctionImageRepository;
    }

    public AuctionEntity createAuction(CreateAuctionReq req){
        AuctionEntity auction = req.getAuction();
        Optional<UserEntity> user = userRepository.findById(req.getUserId());
        auction.setCreator(user.get());
        auctionRepository.save(auction);
        for (String url: req.getUrls()) {
            ImageEntity pic = ImageEntity.builder().imgUrl(url).auction(auction).build();
            auctionImageRepository.save(pic);
        }
        return auction;
    }

}
