package fontys.s3.carspacebackend.controller;

import fontys.s3.carspacebackend.business.service.IBidService;
import fontys.s3.carspacebackend.configuration.security.isauthenticated.IsAuthenticated;
import fontys.s3.carspacebackend.controller.requests.CreateBidReq;
import fontys.s3.carspacebackend.controller.responses.ResourceCreatedResponse;
import fontys.s3.carspacebackend.domain.Bid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/bid")
@AllArgsConstructor
public class BidController{
    private IBidService bidService;
    private final SimpMessagingTemplate simpMessagingTemplate;


    @PostMapping("/{auctionId}") //Maybe not the most RESTful way, auctionId should be a query param
    @IsAuthenticated
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<ResourceCreatedResponse> placeBid(@PathVariable Long auctionId, @RequestBody @Valid CreateBidReq req) throws Exception{
        Bid bid = Bid.builder().amount(req.getAmount()).build();

        Long createdBidId = bidService.createBid(bid, auctionId);

        ResourceCreatedResponse res = ResourceCreatedResponse.builder().message("Bid placed!").id(createdBidId).build();
        sendNotif();
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    public void sendNotif() {
        String message = "Bids pushed from back-end";
        this.simpMessagingTemplate.convertAndSend("/topic/bids", message);
    }

}
