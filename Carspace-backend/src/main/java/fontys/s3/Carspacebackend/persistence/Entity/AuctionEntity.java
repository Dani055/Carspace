package fontys.s3.Carspacebackend.persistence.Entity;


import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

import java.util.List;
import java.util.Set;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="s3carspace_auction")
public class AuctionEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="car_brand", nullable = false)
    private String carBrand;

    @Column(name="car_model", nullable = false)
    private String carModel;

    @Column(name="description")
    private String carDesc;

    @Column(name="car_year", nullable = false)
    private int carYear;

    @Column(name="starting_price", nullable = false)
    private double startingPrice;

    @Column(name="buyout_price", nullable = false)
    private double buyoutPrice;

    @Column(name="mileage", nullable = false)
    private int mileage;

    @Column(name="has_sold", columnDefinition = "bool default FALSE")
    private boolean hasSold;

    @Column(name="location", nullable = false)
    private String location;

    @Column(name="starts_on", nullable = false)
    private Timestamp startsOn;

    @Column(name="ends_on", nullable = false)
    private Timestamp endsOn;

    @ManyToOne(cascade = CascadeType.MERGE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="creator_id", referencedColumnName = "id", nullable = false)
    private UserEntity creator;


    @OneToMany(mappedBy = "auction", fetch = FetchType.LAZY)
    private Set<ImageEntity> images;

    @OneToMany(mappedBy = "auction", fetch = FetchType.LAZY)
    private Set<CommentEntity> comments;

    @OneToMany(mappedBy = "auction", fetch = FetchType.LAZY)
    private Set<BidEntity> bids;

    @OneToOne
    @JoinColumn(name="winning_bid_id", referencedColumnName = "id")
    private BidEntity winningBid;
}
