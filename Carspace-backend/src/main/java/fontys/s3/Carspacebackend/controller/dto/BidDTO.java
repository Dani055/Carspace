package fontys.s3.Carspacebackend.controller.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.time.Instant;


@Data
@Builder
public class BidDTO {
    private Long id;

    private Double amount;

    private Instant createdOn;

    private UserDTO bidder;
}
