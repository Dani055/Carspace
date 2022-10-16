package fontys.s3.Carspacebackend.domain;

import lombok.*;

import java.sql.Timestamp;
import java.util.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Auction {

    private Long id;

    private String carBrand;

    private String carModel;

    private String carDesc;

    private int carYear;

    private double startingPrice;

    private double buyoutPrice;

    private int mileage;

    private boolean hasSold;

    private String location;

    private Timestamp startsOn;

    private Timestamp endsOn;

    private User creator;

    @Singular
    private Set<Image> images = new HashSet<>();

    @Singular
    private Set<Comment> comments = new HashSet<>();

    @Singular
    private Set<Bid> bids = new HashSet<>();

    private Bid winningBid;

    public boolean isOwner(User u){
        if(u.getId() == this.creator.getId())
        {
            return true;
        }
        return false;
    }

    public boolean hasStarted(){
        java.util.Date date = new Date();
        Timestamp now = new Timestamp(date.getTime());
        if(now.after(startsOn)){
            return true;
        }
        return false;
    }
}