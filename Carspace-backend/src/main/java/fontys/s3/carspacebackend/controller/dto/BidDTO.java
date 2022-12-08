package fontys.s3.carspacebackend.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BidDTO {
    private Long id;

    private Double amount;

    private Instant createdOn;

    private UserDTO bidder;
    private Long auctionId;
}
