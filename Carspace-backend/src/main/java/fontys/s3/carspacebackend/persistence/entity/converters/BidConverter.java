package fontys.s3.carspacebackend.persistence.entity.converters;

import fontys.s3.carspacebackend.domain.Bid;

import fontys.s3.carspacebackend.controller.dto.BidDTO;
import fontys.s3.carspacebackend.persistence.entity.BidEntity;


public class BidConverter {
    private BidConverter(){

    }
    public static Bid convertToPOJO(BidEntity b){
        if(b == null){
            return null;
        }
        return Bid.builder().id(b.getId())
                .amount(b.getAmount())
                .createdOn(b.getCreatedOn())
                .bidder(UserConverter.convertToPOJO(b.getBidder()))
                .build();
    }
    public static BidEntity convertToEntity(Bid b){
        if(b == null){
            return null;
        }
        return BidEntity.builder().id(b.getId())
                .amount(b.getAmount())
                .createdOn(b.getCreatedOn())
                .bidder(UserConverter.convertToEntity(b.getBidder()))
                .build();
    }
    public static BidDTO convertToDTO(Bid b){
        if(b == null){
            return null;
        }
        return BidDTO.builder().id(b.getId())
                .amount(b.getAmount())
                .createdOn(b.getCreatedOn())
                .bidder(UserConverter.convertToDTO(b.getBidder()))
                .build();
    }
}
