package fontys.s3.carspacebackend.controller;

import fontys.s3.carspacebackend.business.service.IBidService;
import fontys.s3.carspacebackend.configuration.security.isauthenticated.IsAuthenticated;
import fontys.s3.carspacebackend.controller.requests.CreateBidReq;
import fontys.s3.carspacebackend.controller.responses.ResourceCreatedResponse;
import fontys.s3.carspacebackend.domain.Bid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/bid")
@AllArgsConstructor
public class BidController {
    private IBidService bidService;

    @PostMapping("/{auctionId}")
    @IsAuthenticated
    public ResponseEntity<ResourceCreatedResponse> placeBid(@PathVariable Long auctionId, @RequestBody @Valid CreateBidReq req){
        Bid bid = Bid.builder().amount(req.getAmount()).build();

        Long createdBidId = bidService.createBid(bid, auctionId);

        ResourceCreatedResponse res = ResourceCreatedResponse.builder().message("Bid placed!").id(createdBidId).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }
}
