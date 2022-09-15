package fontys.s3.Carspacebackend.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="s3carspace_auction_comment")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="creator_id", referencedColumnName = "id", nullable = false)
    private UserEntity creator;

    @ManyToOne(cascade = CascadeType.MERGE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="auction_id", referencedColumnName = "id", nullable = false)
    private AuctionEntity auction;

    @Column(name="text", nullable = false)
    private String text;

    @Column(name="created_on", nullable = false, columnDefinition = "Datetime default CURRENT_TIMESTAMP")
    private Date createdOn;
}
