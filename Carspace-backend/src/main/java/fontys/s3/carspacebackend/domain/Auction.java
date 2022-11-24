package fontys.s3.carspacebackend.domain;

import lombok.*;

import java.time.Instant;
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

    private Instant startsOn;

    private Instant endsOn;

    private User creator;

    @Singular
    private Set<Image> images;

    @Singular
    private Set<Comment> comments;

    @Singular
    private Set<Bid> bids;

    private Bid winningBid;

    public boolean isOwner(User u){
        return u.getId().equals(this.creator.getId());
    }

    public boolean hasStarted(){
        Instant now = TimeHelper.Now();
        return now.isAfter(startsOn);
    }
    public boolean hasEnded(){
        Instant now = TimeHelper.Now();
        return now.isAfter(endsOn);
    }
}