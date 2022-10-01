package fontys.s3.Carspacebackend.persistence.repository.impl;

import fontys.s3.Carspacebackend.business.interfaces.IAuctionImageRepository;
import fontys.s3.Carspacebackend.converters.ImageConverter;
import fontys.s3.Carspacebackend.converters.UserConverter;
import fontys.s3.Carspacebackend.domain.Image;
import fontys.s3.Carspacebackend.domain.User;
import fontys.s3.Carspacebackend.persistence.Entity.AuctionEntity;
import fontys.s3.Carspacebackend.persistence.Entity.ImageEntity;
import fontys.s3.Carspacebackend.persistence.Entity.UserEntity;
import fontys.s3.Carspacebackend.persistence.repository.IJPAAuctionImageRepository;
import fontys.s3.Carspacebackend.persistence.repository.IJPAAuctionRepository;
import fontys.s3.Carspacebackend.persistence.repository.IJPAUserRepository;
import org.springframework.stereotype.Repository;

@Repository
public class AuctionImageRepository implements IAuctionImageRepository {
    private final IJPAAuctionImageRepository imgRepository;

    private final IJPAAuctionRepository auctionRepository;

    public AuctionImageRepository(IJPAAuctionImageRepository imgRepo, IJPAAuctionRepository aucRepo){
        imgRepository = imgRepo;
        auctionRepository = aucRepo;
    }

    @Override
    public void saveImage(Image i, Long auctionId){
        AuctionEntity forAuction = auctionRepository.findById(auctionId).get();
        ImageEntity entity = ImageConverter.convertToEntity(i);
        entity.setAuction(forAuction);
        imgRepository.save(entity);
    }
}
