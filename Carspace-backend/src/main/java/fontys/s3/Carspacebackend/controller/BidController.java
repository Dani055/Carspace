package fontys.s3.Carspacebackend.controller;

import fontys.s3.Carspacebackend.business.service.IBidService;
import fontys.s3.Carspacebackend.configuration.security.isauthenticated.IsAuthenticated;
import fontys.s3.Carspacebackend.controller.requests.CreateBidReq;
import fontys.s3.Carspacebackend.controller.responses.ResourceCreatedResponse;
import fontys.s3.Carspacebackend.domain.Bid;
import fontys.s3.Carspacebackend.exception.BadTokenException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
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
