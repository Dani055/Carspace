package fontys.s3.Carspacebackend.controller;

import fontys.s3.Carspacebackend.business.service.IAuctionService;
import fontys.s3.Carspacebackend.business.service.IUserService;
import fontys.s3.Carspacebackend.domain.Auction;
import fontys.s3.Carspacebackend.domain.requests.CreateAuctionReq;
import fontys.s3.Carspacebackend.domain.responses.GenericObjectResponse;
import fontys.s3.Carspacebackend.domain.responses.ResourceCreatedResponse;
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
    public ResponseEntity<ResourceCreatedResponse> createAuction(@RequestHeader HttpHeaders headers, @RequestBody @Valid CreateAuctionReq req){
        String token = headers.getFirst(HttpHeaders.AUTHORIZATION).split("Bearer ")[1];
        Long userId;
        try {
            userId = Long.parseLong(token);
        } catch (NumberFormatException e) {
            throw new BadTokenException();
        }
        req.setUserId(userId);
        Auction toCreate = Auction.builder().carBrand(req.getCarBrand()).carModel(req.getCarModel()).carDesc(req.getCarDesc()).carYear(req.getCarYear()).startingPrice(req.getStartingPrice()).buyoutPrice(req.getBuyoutPrice()).mileage(req.getMileage()).location(req.getLocation()).startsOn(req.getStartsOn()).endsOn(req.getEndsOn()).build();
        Long createdAuctionId = auctionService.createAuction(toCreate, userId, req.getUrls());
        ResourceCreatedResponse res = ResourceCreatedResponse.builder().message("Auction created!").id(createdAuctionId).build();
        return ResponseEntity.ok(res);
    }

}
