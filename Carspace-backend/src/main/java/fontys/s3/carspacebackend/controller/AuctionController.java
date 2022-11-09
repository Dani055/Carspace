package fontys.s3.carspacebackend.controller;

import fontys.s3.carspacebackend.business.service.IAuctionService;

import fontys.s3.carspacebackend.configuration.security.isauthenticated.IsAuthenticated;
import fontys.s3.carspacebackend.converters.AuctionConverter;
import fontys.s3.carspacebackend.domain.Auction;
import fontys.s3.carspacebackend.controller.dto.AuctionDTO;
import fontys.s3.carspacebackend.controller.requests.CreateAuctionReq;
import fontys.s3.carspacebackend.controller.responses.GenericObjectResponse;
import fontys.s3.carspacebackend.controller.responses.ResourceChangedResponse;
import fontys.s3.carspacebackend.controller.responses.ResourceCreatedResponse;
import fontys.s3.carspacebackend.controller.responses.ResourceDeletedResponse;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/auction")
@AllArgsConstructor
public class AuctionController {
//    private IUserService userService;
    private final IAuctionService auctionService;

    @PostMapping()
    @IsAuthenticated
    public ResponseEntity<ResourceCreatedResponse> createAuction(@RequestHeader HttpHeaders headers, @RequestBody @Valid CreateAuctionReq req){
        Auction toCreate = Auction.builder().carBrand(req.getCarBrand()).carModel(req.getCarModel()).carDesc(req.getCarDesc()).carYear(req.getCarYear()).startingPrice(req.getStartingPrice()).buyoutPrice(req.getBuyoutPrice()).mileage(req.getMileage()).location(req.getLocation()).startsOn(req.getStartsOn()).endsOn(req.getEndsOn()).build();
        Long createdAuctionId = auctionService.createAuction(toCreate, req.getUrls());
        ResourceCreatedResponse res = ResourceCreatedResponse.builder().message("Auction created!").id(createdAuctionId).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
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
        AuctionDTO a = AuctionConverter.convertToDTO(auction);
        return ResponseEntity.ok(a);
    }

    @PutMapping("/{auctionId}")
    @IsAuthenticated
    public ResponseEntity<ResourceChangedResponse> editAuction(@PathVariable Long auctionId, @RequestBody @Valid CreateAuctionReq req){
        Auction toEdit = Auction.builder().id(auctionId).carBrand(req.getCarBrand()).carModel(req.getCarModel()).carDesc(req.getCarDesc()).carYear(req.getCarYear()).startingPrice(req.getStartingPrice()).buyoutPrice(req.getBuyoutPrice()).mileage(req.getMileage()).location(req.getLocation()).startsOn(req.getStartsOn()).endsOn(req.getEndsOn()).build();

        Long editedAuctionId = auctionService.editAuction(toEdit, req.getUrls());

        ResourceChangedResponse res = ResourceChangedResponse.builder().message("Auction edited!").id(editedAuctionId).build();
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/{auctionId}")
    @IsAuthenticated
    public ResponseEntity<ResourceDeletedResponse> deleteAuction(@PathVariable Long auctionId){
        auctionService.deleteAuction(auctionId);

        ResourceDeletedResponse res = ResourceDeletedResponse.builder().message("Auction deleted").build();
        return ResponseEntity.ok(res);
    }
}
