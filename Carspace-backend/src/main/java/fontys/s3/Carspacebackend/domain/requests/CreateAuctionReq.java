package fontys.s3.Carspacebackend.domain.requests;

import fontys.s3.Carspacebackend.persistence.Entity.AuctionEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAuctionReq {
    private List<String> urls;
    private Long userId;

    private AuctionEntity auction;
}
