package fontys.s3.carspacebackend.business.validator;

import fontys.s3.carspacebackend.domain.Auction;

public interface IAuctionValidator {
    boolean ValidateDatesForModification(Auction auction);
}
