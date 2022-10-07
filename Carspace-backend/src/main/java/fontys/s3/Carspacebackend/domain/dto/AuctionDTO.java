package fontys.s3.Carspacebackend.domain.dto;

import fontys.s3.Carspacebackend.domain.Bid;
import fontys.s3.Carspacebackend.domain.Comment;
import fontys.s3.Carspacebackend.domain.Image;
import fontys.s3.Carspacebackend.domain.User;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class AuctionDTO {
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

    private UserDTO creator;

    @Singular
    private Set<Image> images;

    @Singular
    private Set<CommentDTO> comments;

    @Singular
    private Set<BidDTO> bids;

    private BidDTO winningBid;
}
