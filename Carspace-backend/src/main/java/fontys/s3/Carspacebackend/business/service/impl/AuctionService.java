package fontys.s3.Carspacebackend.business.service.impl;

import fontys.s3.Carspacebackend.business.interfaces.IAuctionImageRepository;
import fontys.s3.Carspacebackend.business.interfaces.IAuctionRepository;
import fontys.s3.Carspacebackend.business.interfaces.IUserRepository;
import fontys.s3.Carspacebackend.business.service.IAuctionService;
import fontys.s3.Carspacebackend.domain.Auction;
import fontys.s3.Carspacebackend.domain.Image;
import fontys.s3.Carspacebackend.domain.requests.CreateAuctionReq;
import fontys.s3.Carspacebackend.persistence.Entity.AuctionEntity;
import fontys.s3.Carspacebackend.persistence.Entity.ImageEntity;
import fontys.s3.Carspacebackend.persistence.repository.IJPAAuctionImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

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

    @Transactional
    public Long createAuction(Auction auc, Long userId, List<String> urls){
        Long auctionId = auctionRepository.saveAuction(auc, userId);
        for (String url: urls) {
            Image pic = Image.builder().imgUrl(url).build();
            auctionImageRepository.saveImage(pic, auctionId);
        }
        return auctionId;
    }

    public List<Auction> getAuctions(){
        return auctionRepository.getAuctions();
    }

    public Auction getAuctionDetails(Long id){
        return auctionRepository.getAuctionById(id);
    }
}
