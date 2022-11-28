package fontys.s3.carspacebackend.persistence.entity.converters;

import fontys.s3.carspacebackend.domain.Auction;
import fontys.s3.carspacebackend.domain.Bid;
import fontys.s3.carspacebackend.domain.Comment;
import fontys.s3.carspacebackend.domain.Image;
import fontys.s3.carspacebackend.controller.dto.AuctionDTO;
import fontys.s3.carspacebackend.controller.dto.BidDTO;
import fontys.s3.carspacebackend.controller.dto.CommentDTO;
import fontys.s3.carspacebackend.persistence.entity.AuctionEntity;

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
            images = a.getImages().stream().map(ImageConverter::convertToPOJO).collect(Collectors.toCollection(LinkedHashSet::new));
        }

        if(a.getComments() == null){
            comments = new HashSet<>();
        }
        else{
            comments = a.getComments().stream().map(CommentConverter::convertToPOJO).collect(Collectors.toCollection(LinkedHashSet::new));
        }

        if(a.getBids() == null){
            bids = new HashSet<>();
        }
        else{
            bids = a.getBids().stream().map(BidConverter::convertToPOJO).collect(Collectors.toCollection(LinkedHashSet::new));
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
                .images(a.getImages().stream().map(ImageConverter::convertToEntity).collect(Collectors.toSet()))
                .comments(a.getComments().stream().map(CommentConverter::convertToEntity).collect(Collectors.toSet()))
                .bids(a.getBids().stream().map(BidConverter::convertToEntity).collect(Collectors.toSet()))
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
            comments = a.getComments().stream().map(CommentConverter::convertToDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        }

        if(a.getBids() == null){
            bids = new HashSet<>();
        }
        else{
            bids = a.getBids().stream().map(BidConverter::convertToDTO).collect(Collectors.toCollection(LinkedHashSet::new));
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
