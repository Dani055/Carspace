package fontys.s3.Carspacebackend.converters;

import fontys.s3.Carspacebackend.domain.Auction;
import fontys.s3.Carspacebackend.persistence.Entity.AuctionEntity;
import fontys.s3.Carspacebackend.persistence.Entity.ImageEntity;

import java.util.stream.Collectors;

public class AuctionConverter {
    private AuctionConverter(){

    }
    public static Auction convertToPOJO(AuctionEntity a){
        return Auction.builder().id(a.getId())
                .carBrand(a.getCarBrand())
                .carModel(a.getCarModel())
                .carDesc(a.getCarDesc())
                .carYear(a.getCarYear())
                .startingPrice(a.getStartingPrice())
                .buyoutPrice(a.getBuyoutPrice())
                .mileage(a.getMileage())
                .hasSold(a.isHasSold())
                .location(a.getLocation())
                .startsOn(a.getStartsOn())
                .endsOn(a.getEndsOn())
                .creator(UserConverter.convertToPOJO(a.getCreator()))
                .images(a.getImages().stream().map(imageEntity -> ImageConverter.convertToPOJO(imageEntity)).collect(Collectors.toSet()))
                .comments(a.getComments().stream().map(commentEntity -> CommentConverter.convertToPOJO(commentEntity)).collect(Collectors.toSet()))
                .bids(a.getBids().stream().map(bidEntity -> BidConverter.convertToPOJO(bidEntity)).collect(Collectors.toSet()))
                .winningBid(BidConverter.convertToPOJO(a.getWinningBid()))
                .build();
    }
    public static AuctionEntity convertToEntity(Auction a){

        return AuctionEntity.builder().id(a.getId())
                .carBrand(a.getCarBrand())
                .carModel(a.getCarModel())
                .carDesc(a.getCarDesc())
                .carYear(a.getCarYear())
                .startingPrice(a.getStartingPrice())
                .buyoutPrice(a.getBuyoutPrice())
                .mileage(a.getMileage())
                .hasSold(a.isHasSold())
                .location(a.getLocation())
                .startsOn(a.getStartsOn())
                .endsOn(a.getEndsOn())
                .creator(UserConverter.convertToEntity(a.getCreator()))
                .images(a.getImages().stream().map(imageEntity -> ImageConverter.convertToEntity(imageEntity)).collect(Collectors.toSet()))
                .comments(a.getComments().stream().map(commentEntity -> CommentConverter.convertToEntity(commentEntity)).collect(Collectors.toSet()))
                .bids(a.getBids().stream().map(bidEntity -> BidConverter.convertToEntity(bidEntity)).collect(Collectors.toSet()))
                .winningBid(BidConverter.convertToEntity(a.getWinningBid()))
                .build();
    }
}
