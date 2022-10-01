package fontys.s3.Carspacebackend.persistence.repository.impl;

import fontys.s3.Carspacebackend.business.interfaces.IAuctionRepository;
import fontys.s3.Carspacebackend.converters.AuctionConverter;
import fontys.s3.Carspacebackend.converters.UserConverter;
import fontys.s3.Carspacebackend.domain.Auction;
import fontys.s3.Carspacebackend.domain.User;
import fontys.s3.Carspacebackend.persistence.Entity.AuctionEntity;
import fontys.s3.Carspacebackend.persistence.Entity.UserEntity;
import fontys.s3.Carspacebackend.persistence.repository.IJPAAuctionImageRepository;
import fontys.s3.Carspacebackend.persistence.repository.IJPAAuctionRepository;
import fontys.s3.Carspacebackend.persistence.repository.IJPAUserRepository;
import org.springframework.stereotype.Repository;

@Repository
public class AuctionRepository implements IAuctionRepository {
    private final IJPAUserRepository userRepository;
    private final IJPAAuctionRepository auctionRepository;

    public AuctionRepository(IJPAUserRepository uRepo, IJPAAuctionRepository aRepo){
        userRepository = uRepo;
        auctionRepository = aRepo;
    }
    @Override
    public Long saveAuction(Auction auction, Long userId){
        UserEntity userEntity = userRepository.findById(userId).get();
        AuctionEntity auctionEntity = AuctionConverter.convertToEntity(auction);
        auctionEntity.setCreator(userEntity);
        return auctionRepository.save(auctionEntity).getId();
    }
}
