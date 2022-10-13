package fontys.s3.Carspacebackend.persistence.repository.impl;

import fontys.s3.Carspacebackend.business.interfaces.IAuctionRepository;
import fontys.s3.Carspacebackend.converters.AuctionConverter;
import fontys.s3.Carspacebackend.converters.UserConverter;
import fontys.s3.Carspacebackend.domain.Auction;
import fontys.s3.Carspacebackend.domain.Image;
import fontys.s3.Carspacebackend.domain.User;
import fontys.s3.Carspacebackend.exception.ResourceNotFoundException;
import fontys.s3.Carspacebackend.persistence.Entity.AuctionEntity;
import fontys.s3.Carspacebackend.persistence.Entity.ImageEntity;
import fontys.s3.Carspacebackend.persistence.Entity.UserEntity;
import fontys.s3.Carspacebackend.persistence.repository.IJPAAuctionImageRepository;
import fontys.s3.Carspacebackend.persistence.repository.IJPAAuctionRepository;
import fontys.s3.Carspacebackend.persistence.repository.IJPAUserRepository;
import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class AuctionRepository implements IAuctionRepository {
    private final IJPAUserRepository userRepository;
    private final IJPAAuctionRepository auctionRepository;

    private final IJPAAuctionImageRepository imageRepository;

    @Override
    public Long saveAuction(Auction auction, Long userId, List<String> urls){
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if(userEntity.isEmpty()){
            throw new ResourceNotFoundException("User", "id", userId);
        }
        AuctionEntity auctionEntity = AuctionConverter.convertToEntity(auction);
        auctionEntity.setCreator(userEntity.get());
        auctionRepository.save(auctionEntity);

        List<ImageEntity> toAdd = new ArrayList<>();
        for (String url: urls) {
            ImageEntity pic = ImageEntity.builder().imgUrl(url).auction(auctionEntity).build();
            toAdd.add(pic);
        }
        imageRepository.saveAll(toAdd);
        return auctionRepository.save(auctionEntity).getId();
    }

    @Override
    public List<Auction> getAuctions(){
        List<AuctionEntity> auctionsEntities = auctionRepository.findAll();

        List<Auction> auctions = auctionsEntities.stream().map(auctionEntity -> AuctionConverter.convertToPOJO(auctionEntity)).collect(Collectors.toList());
        return auctions;
    }

    @Override
    public Auction getAuctionById(Long aucId){
        Optional<AuctionEntity> auction = auctionRepository.findById(aucId);
        if(auction.isEmpty()){
            throw new ResourceNotFoundException("Auction", "id", aucId);
        }
        return AuctionConverter.convertToPOJO(auction.get());
    }

    @Override
    public Long changeAuctionInfo(Auction a, List<String> urls){
        Optional<AuctionEntity> ae = auctionRepository.findById(a.getId());
        if(ae.isEmpty()){
            throw new ResourceNotFoundException("Auction", "id", a.getId());
        }
        AuctionEntity entity = ae.get();
        imageRepository.deleteAll(entity.getImages());

        List<ImageEntity> toAdd = new ArrayList<>();
        for (String url: urls) {
            ImageEntity pic = ImageEntity.builder().imgUrl(url).auction(entity).build();
            toAdd.add(pic);
        }

        imageRepository.saveAll(toAdd);

        entity.setCarBrand(a.getCarBrand());
        entity.setCarModel(a.getCarModel());
        entity.setCarDesc(a.getCarDesc());
        entity.setCarYear(a.getCarYear());
        entity.setStartingPrice(a.getStartingPrice());
        entity.setBuyoutPrice(a.getBuyoutPrice());
        entity.setMileage(a.getMileage());
        entity.setLocation(a.getLocation());
        entity.setStartsOn(a.getStartsOn());
        entity.setEndsOn(a.getEndsOn());
        return auctionRepository.save(entity).getId();
    }

    @Override
    public boolean deleteAuction(Long auctionId){
        auctionRepository.deleteById(auctionId);
        return true;
    }
}
