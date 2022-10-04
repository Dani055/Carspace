package fontys.s3.Carspacebackend.controller;

import fontys.s3.Carspacebackend.business.service.IAuctionService;
import fontys.s3.Carspacebackend.business.service.IUserService;
import fontys.s3.Carspacebackend.converters.AuctionConverter;
import fontys.s3.Carspacebackend.domain.Auction;
import fontys.s3.Carspacebackend.domain.dto.AuctionDTO;
import fontys.s3.Carspacebackend.domain.requests.CreateAuctionReq;
import fontys.s3.Carspacebackend.domain.responses.GenericObjectResponse;
import fontys.s3.Carspacebackend.domain.responses.ResourceCreatedResponse;
import fontys.s3.Carspacebackend.exception.BadTokenException;
import fontys.s3.Carspacebackend.persistence.Entity.AuctionEntity;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auction")
@AllArgsConstructor
public class AuctionController {
//    private IUserService userService;
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

    @GetMapping
    public ResponseEntity<GenericObjectResponse> getAllAuctions(){
        List<Auction> auctions = auctionService.getAuctions();
        List<AuctionDTO> dtos = auctions.stream().map(a -> AuctionConverter.convertToDTO(a)).collect(Collectors.toList());
        GenericObjectResponse res = GenericObjectResponse.builder().message("Successfully fetched auctions").obj(dtos).build();
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{auctionId}")
    public ResponseEntity<AuctionDTO> getAuctionDetails(@PathVariable Long auctionId){
        Auction auction = auctionService.getAuctionDetails(auctionId);
        return ResponseEntity.ok(AuctionConverter.convertToDTO(auction));
    }
}
