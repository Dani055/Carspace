package fontys.s3.Carspacebackend.business.validator;

import fontys.s3.Carspacebackend.domain.Auction;

public interface IAuctionValidator {
    boolean ValidateDatesForModification(Auction auction);
}
