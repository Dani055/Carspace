package fontys.s3.Carspacebackend.domain;

import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Auction {

    private Long id;

    private String carBrand;

    private String carModel;

    private String carDesc;

    private Date carYear;

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
}