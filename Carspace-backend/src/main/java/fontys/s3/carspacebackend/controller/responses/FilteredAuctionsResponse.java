package fontys.s3.carspacebackend.controller.responses;

import fontys.s3.carspacebackend.controller.dto.AuctionDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilteredAuctionsResponse {
    Long totalItems;
    List<AuctionDTO> auctions;
    int currentPage;
    int totalPages;
}
