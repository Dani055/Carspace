package fontys.s3.Carspacebackend.converters;

import fontys.s3.Carspacebackend.domain.Bid;

import fontys.s3.Carspacebackend.persistence.Entity.BidEntity;


public class BidConverter {
    private BidConverter(){

    }
    public static Bid convertToPOJO(BidEntity b){
        return Bid.builder().id(b.getId())
                .amount(b.getAmount())
                .createdOn(b.getCreatedOn())
                .build();
    }
    public static BidEntity convertToEntity(Bid b){
        if(b == null){
            return null;
        }
        return BidEntity.builder().id(b.getId())
                .amount(b.getAmount())
                .createdOn(b.getCreatedOn())
                .build();
    }
}
