package fontys.s3.Carspacebackend.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Entity
@Table(name="s3carspace_auction_bid")
public class BidEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="bidder_id", referencedColumnName = "id", nullable = false)
    @EqualsAndHashCode.Exclude
    private UserEntity bidder;


    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="auction_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private AuctionEntity auction;

    @Column(name="amount", nullable = false)
    private Double amount;

    @Column(name="created_on", nullable = false, columnDefinition = "Datetime default CURRENT_TIMESTAMP")
    @CreationTimestamp
    private Timestamp createdOn;
}
