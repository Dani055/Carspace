package fontys.s3.Carspacebackend.domain;

import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class Bid {

    private Long id;

    private Double amount;

    private Timestamp createdOn;

    private User bidder;
}
