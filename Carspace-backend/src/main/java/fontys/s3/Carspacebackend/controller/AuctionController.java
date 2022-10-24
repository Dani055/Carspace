package fontys.s3.Carspacebackend.controller;

import fontys.s3.Carspacebackend.business.service.IAuctionService;

import fontys.s3.Carspacebackend.converters.AuctionConverter;
import fontys.s3.Carspacebackend.domain.Auction;
import fontys.s3.Carspacebackend.controller.dto.AuctionDTO;
import fontys.s3.Carspacebackend.controller.requests.CreateAuctionReq;
import fontys.s3.Carspacebackend.controller.responses.GenericObjectResponse;
import fontys.s3.Carspacebackend.controller.responses.ResourceChangedResponse;
import fontys.s3.Carspacebackend.controller.responses.ResourceCreatedResponse;
import fontys.s3.Carspacebackend.controller.responses.ResourceDeletedResponse;
import fontys.s3.Carspacebackend.exception.BadTokenException;

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
    private IAuctionService auctionService;

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
    @PostMapping()
    public ResponseEntity<ResourceCreatedResponse> createAuction(@RequestHeader HttpHeaders headers, @RequestBody @Valid CreateAuctionReq req){
        Long userId = extractToken(headers);

        Auction toCreate = Auction.builder().carBrand(req.getCarBrand()).carModel(req.getCarModel()).carDesc(req.getCarDesc()).carYear(req.getCarYear()).startingPrice(req.getStartingPrice()).buyoutPrice(req.getBuyoutPrice()).mileage(req.getMileage()).location(req.getLocation()).startsOn(req.getStartsOn()).endsOn(req.getEndsOn()).build();
        Long createdAuctionId = auctionService.createAuction(toCreate, userId, req.getUrls());
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
    public ResponseEntity<ResourceChangedResponse> editAuction(@PathVariable Long auctionId, @RequestHeader HttpHeaders headers, @RequestBody @Valid CreateAuctionReq req){
        Long userId = extractToken(headers);
        Auction toEdit = Auction.builder().id(auctionId).carBrand(req.getCarBrand()).carModel(req.getCarModel()).carDesc(req.getCarDesc()).carYear(req.getCarYear()).startingPrice(req.getStartingPrice()).buyoutPrice(req.getBuyoutPrice()).mileage(req.getMileage()).location(req.getLocation()).startsOn(req.getStartsOn()).endsOn(req.getEndsOn()).build();

        Long editedAuctionId = auctionService.editAuction(toEdit, userId, req.getUrls());

        ResourceChangedResponse res = ResourceChangedResponse.builder().message("Auction edited!").id(editedAuctionId).build();
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/{auctionId}")
    public ResponseEntity<ResourceDeletedResponse> deleteAuction(@PathVariable Long auctionId, @RequestHeader HttpHeaders headers){
        Long userId = extractToken(headers);

        auctionService.deleteAuction(auctionId, userId);

        ResourceDeletedResponse res = ResourceDeletedResponse.builder().message("Auction deleted").build();
        return ResponseEntity.ok(res);
    }
}
