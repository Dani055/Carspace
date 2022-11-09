package fontys.s3.carspacebackend.controller.dto;

import fontys.s3.carspacebackend.domain.Image;
import lombok.*;

import java.time.Instant;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    private Instant startsOn;

    private Instant endsOn;

    private UserDTO creator;

    @Singular
    private Set<Image> images;

    @Singular
    private Set<CommentDTO> comments;

    @Singular
    private Set<BidDTO> bids;

    private BidDTO winningBid;
}
