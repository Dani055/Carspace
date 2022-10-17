package fontys.s3.Carspacebackend.controller;

import fontys.s3.Carspacebackend.business.service.IBidService;
import fontys.s3.Carspacebackend.controller.requests.CreateBidReq;
import fontys.s3.Carspacebackend.controller.responses.ResourceCreatedResponse;
import fontys.s3.Carspacebackend.domain.Bid;
import fontys.s3.Carspacebackend.exception.BadTokenException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/bid")
@AllArgsConstructor
public class BidController {
    private IBidService bidService;
    private Long extractToken(HttpHeaders headers){
        if(headers.getFirst(HttpHeaders.AUTHORIZATION) == null){
            throw new BadTokenException();
        }
        String token = headers.getFirst(HttpHeaders.AUTHORIZATION).split("Bearer ")[1];
        Long userId;
        try {
            userId = Long.parseLong(token);
        } catch (NumberFormatException e) {
            throw new BadTokenException();
        }
        return userId;
    }

    @PostMapping("/{auctionId}")
    public ResponseEntity<ResourceCreatedResponse> placeBid(@PathVariable Long auctionId, @RequestHeader HttpHeaders headers, @RequestBody @Valid CreateBidReq req){
        Long userId = extractToken(headers);
        Bid bid = Bid.builder().amount(req.getAmount()).build();

        Long createdBidId = bidService.createBid(bid, auctionId, userId);

        ResourceCreatedResponse res = ResourceCreatedResponse.builder().message("Bid placed!").id(createdBidId).build();
        return ResponseEntity.ok(res);
    }
}
