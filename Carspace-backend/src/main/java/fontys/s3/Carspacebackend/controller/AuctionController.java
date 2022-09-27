package fontys.s3.Carspacebackend.controller;

import fontys.s3.Carspacebackend.business.service.IAuctionService;
import fontys.s3.Carspacebackend.business.service.IUserService;
import fontys.s3.Carspacebackend.domain.requests.CreateAuctionReq;
import fontys.s3.Carspacebackend.domain.responses.GenericObjectResponse;
import fontys.s3.Carspacebackend.exception.BadTokenException;
import fontys.s3.Carspacebackend.persistence.Entity.AuctionEntity;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auction")
@AllArgsConstructor
public class AuctionController {
    private IUserService userService;
    private IAuctionService auctionService;

    @PostMapping()
    public ResponseEntity<GenericObjectResponse> createAuction(@RequestHeader HttpHeaders headers, @RequestBody @Valid CreateAuctionReq req){
        String token = headers.getFirst(HttpHeaders.AUTHORIZATION).split("Bearer ")[1];
        Long userId;
        try {
            userId = Long.parseLong(token);
        } catch (NumberFormatException e) {
            throw new BadTokenException();
        }
        req.setUserId(userId);
        AuctionEntity auction = auctionService.createAuction(req);
        GenericObjectResponse res = GenericObjectResponse.builder().message("Auction created!").obj(auction).build();
        return ResponseEntity.ok(res);
    }

}
