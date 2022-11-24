package fontys.s3.carspacebackend.domain;

import lombok.*;

import java.time.Instant;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class Bid {

    private Long id;

    private Double amount;

    private Instant createdOn;

    private User bidder;
}
