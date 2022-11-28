package fontys.s3.carspacebackend.persistence.repository.impl;

import fontys.s3.carspacebackend.business.interfaces.IAuctionImageRepository;
import fontys.s3.carspacebackend.persistence.entity.converters.ImageConverter;

import fontys.s3.carspacebackend.domain.Image;

import fontys.s3.carspacebackend.exception.ResourceNotFoundException;
import fontys.s3.carspacebackend.persistence.entity.AuctionEntity;
import fontys.s3.carspacebackend.persistence.entity.ImageEntity;

import fontys.s3.carspacebackend.persistence.repository.IJPAAuctionImageRepository;
import fontys.s3.carspacebackend.persistence.repository.IJPAAuctionRepository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class AuctionImageRepository implements IAuctionImageRepository {
    private final IJPAAuctionImageRepository imgRepository;

    private final IJPAAuctionRepository auctionRepository;

    @Override
    public void saveImage(Image i, Long auctionId){
        Optional<AuctionEntity> forAuction = auctionRepository.findById(auctionId);

        if(forAuction.isEmpty()){
            throw new ResourceNotFoundException("Auction", "id", auctionId);
        }

        ImageEntity entity = ImageConverter.convertToEntity(i);
        entity.setAuction(forAuction.get());
        imgRepository.save(entity);
    }
}
