package fontys.s3.carspacebackend.persistence.repository.impl;

import fontys.s3.carspacebackend.business.interfaces.IAuctionRepository;

import fontys.s3.carspacebackend.persistence.entity.converters.AuctionConverter;
import fontys.s3.carspacebackend.domain.Auction;
import fontys.s3.carspacebackend.domain.AuctionFilters;
import fontys.s3.carspacebackend.exception.ResourceNotFoundException;
import fontys.s3.carspacebackend.persistence.entity.AuctionEntity;
import fontys.s3.carspacebackend.persistence.entity.ImageEntity;
import fontys.s3.carspacebackend.persistence.entity.UserEntity;

import fontys.s3.carspacebackend.persistence.repository.IJPAAuctionImageRepository;
import fontys.s3.carspacebackend.persistence.repository.IJPAAuctionRepository;
import fontys.s3.carspacebackend.persistence.repository.IJPAUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public List<Auction> getAuctionsByCreator(long creatorId){
        List<AuctionEntity> auctionsEntities = auctionRepository.findByCreatorId(creatorId);


        return auctionsEntities.stream().map(AuctionConverter::convertToPOJO).toList();

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
        if(!urls.isEmpty()){
            imageRepository.deleteAll(entity.getImages());
        }

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

    @Override
    public Page<Auction> findLiveAuctionsByFilters(AuctionFilters filters, Pageable pageable){
        //Commencing pepega shit
        Page<AuctionEntity> entities;
        if(filters.isHasEnded()){
            entities = auctionRepository.findByCarBrandContainingAndCarModelContainingAndLocationContainingAndCarYearBetweenAndStartingPriceGreaterThanEqualAndBuyoutPriceLessThanEqualAndMileageBetweenAndHasSoldOrderByEndsOnDesc(filters.getCarBrand(),filters.getCarModel(), filters.getLocation(), filters.getMinYear(), filters.getMaxYear(),filters.getMinPrice(), filters.getMaxPrice(),filters.getMinMileage(), filters.getMaxMileage(),true, pageable);
        }
        else{
            entities = auctionRepository.findByCarBrandContainingAndCarModelContainingAndLocationContainingAndCarYearBetweenAndStartingPriceGreaterThanEqualAndBuyoutPriceLessThanEqualAndMileageBetweenAndHasSoldOrderByEndsOnAsc(filters.getCarBrand(),filters.getCarModel(), filters.getLocation(), filters.getMinYear(), filters.getMaxYear(),filters.getMinPrice(), filters.getMaxPrice(),filters.getMinMileage(), filters.getMaxMileage(),false, pageable);
        }


        return entities.map(AuctionConverter::convertToPOJO);

    }

    @Override
    public void endAuctions() {
        List<AuctionEntity> unendedAuctions = auctionRepository.findByEndsOnBeforeAndHasSold(Instant.now(), false);
        for (AuctionEntity auction: unendedAuctions) {
            auction.setHasSold(true);
            if(!auction.getBids().isEmpty()){
                //Sorted by jpa, first bid is the highest
                auction.setWinningBid(auction.getBids().iterator().next());
            }
        }
        auctionRepository.saveAll(unendedAuctions);
    }
}
