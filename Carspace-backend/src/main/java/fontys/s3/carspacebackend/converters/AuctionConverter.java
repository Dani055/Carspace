package fontys.s3.carspacebackend.converters;

import fontys.s3.carspacebackend.domain.Auction;
import fontys.s3.carspacebackend.domain.Bid;
import fontys.s3.carspacebackend.domain.Comment;
import fontys.s3.carspacebackend.domain.Image;
import fontys.s3.carspacebackend.controller.dto.AuctionDTO;
import fontys.s3.carspacebackend.controller.dto.BidDTO;
import fontys.s3.carspacebackend.controller.dto.CommentDTO;
import fontys.s3.carspacebackend.persistence.Entity.AuctionEntity;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AuctionConverter {
    private AuctionConverter(){

    }
    public static Auction convertToPOJO(AuctionEntity a){
        Set<Image> images;
        Set<Comment> comments;
        Set<Bid> bids;
        if(a.getImages() == null){
            images = new HashSet<>();
        }
        else{
            images = a.getImages().stream().map(imageEntity -> ImageConverter.convertToPOJO(imageEntity)).collect(Collectors.toCollection(LinkedHashSet::new));
        }

        if(a.getComments() == null){
            comments = new HashSet<>();
        }
        else{
            comments = a.getComments().stream().map(commentEntity -> CommentConverter.convertToPOJO(commentEntity)).collect(Collectors.toCollection(LinkedHashSet::new));
        }

        if(a.getBids() == null){
            bids = new HashSet<>();
        }
        else{
            bids = a.getBids().stream().map(bidEntity -> BidConverter.convertToPOJO(bidEntity)).collect(Collectors.toCollection(LinkedHashSet::new));
        }

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
                .images(images)
                .comments(comments)
                .bids(bids)
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

    public static AuctionDTO convertToDTO(Auction a){
        Set<CommentDTO> comments;
        Set<BidDTO> bids;
        if(a.getComments() == null){
            comments = new HashSet<>();
        }
        else{
            comments = a.getComments().stream().map(comment -> CommentConverter.convertToDTO(comment)).collect(Collectors.toCollection(LinkedHashSet::new));
        }

        if(a.getBids() == null){
            bids = new HashSet<>();
        }
        else{
            bids = a.getBids().stream().map(bid -> BidConverter.convertToDTO(bid)).collect(Collectors.toCollection(LinkedHashSet::new));
        }

        return AuctionDTO.builder().id(a.getId())
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
                .creator(UserConverter.convertToDTO(a.getCreator()))
                .images(a.getImages())
                .comments(comments)
                .bids(bids)
                .winningBid(BidConverter.convertToDTO(a.getWinningBid()))
                .build();
    }
}
