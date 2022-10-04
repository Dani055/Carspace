package fontys.s3.Carspacebackend.domain.dto;

import fontys.s3.Carspacebackend.domain.User;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class BidDTO {
    private Long id;

    private Double amount;

    private Date createdOn;

    private UserDTO bidder;
}
