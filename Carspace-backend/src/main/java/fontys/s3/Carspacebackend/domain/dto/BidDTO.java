package fontys.s3.Carspacebackend.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;


@Data
@Builder
public class BidDTO {
    private Long id;

    private Double amount;

    private Timestamp createdOn;

    private UserDTO bidder;
}
