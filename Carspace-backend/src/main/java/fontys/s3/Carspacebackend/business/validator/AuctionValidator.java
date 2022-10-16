package fontys.s3.Carspacebackend.business.validator;

import fontys.s3.Carspacebackend.domain.Auction;
import fontys.s3.Carspacebackend.exception.IncorrectAuctionDates;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Service
public class AuctionValidator implements IAuctionValidator{
    public boolean ValidateDatesForModification(Auction auc){
        Date date = new Date();
        Timestamp now = new Timestamp(date.getTime());

        if(auc.getStartsOn().before(now) || auc.getEndsOn().before(now)){
            throw new IncorrectAuctionDates("Dates cannot be in the past");
        }
        else if(auc.getEndsOn().before(auc.getStartsOn())){
            throw new IncorrectAuctionDates("End date is before start date");
        }
        else if(addDays(now, 1).after(auc.getStartsOn())){
            throw new IncorrectAuctionDates("Auction must start at least 1 day after today");
        }
        else if(addDays(auc.getStartsOn(), 1).after(auc.getEndsOn())){
            throw new IncorrectAuctionDates("Auction must run for at least 1 day");
        }
        return true;
    }
    private Timestamp addDays(Timestamp date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);// w ww.  j ava  2  s  .co m
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return new Timestamp(cal.getTime().getTime());

    }
}
