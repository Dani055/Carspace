package fontys.s3.carspacebackend.business.validator;

import fontys.s3.carspacebackend.domain.Auction;
import fontys.s3.carspacebackend.exception.IncorrectAuctionDates;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class AuctionValidator implements IAuctionValidator{

    public boolean ValidateDatesForModification(Auction auc){
        Instant now = Instant.now();

        if(auc.getStartsOn().isBefore(now) || auc.getEndsOn().isBefore(now)){
            throw new IncorrectAuctionDates("Dates cannot be in the past");
        }
        else if(auc.getEndsOn().isBefore(auc.getStartsOn())){
            throw new IncorrectAuctionDates("End date is before start date");
        }
        else if(now.plus(1, ChronoUnit.DAYS).isAfter(auc.getStartsOn())){
            throw new IncorrectAuctionDates("Auction must start at least 1 day after today");
        }
        else if(auc.getStartsOn().plus(1, ChronoUnit.DAYS).isAfter(auc.getEndsOn())){
            throw new IncorrectAuctionDates("Auction must run for at least 1 day");
        }
        return true;
    }
}
