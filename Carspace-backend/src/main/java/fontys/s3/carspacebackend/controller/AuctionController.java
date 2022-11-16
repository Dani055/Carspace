package fontys.s3.carspacebackend.controller;

import fontys.s3.carspacebackend.business.service.IAuctionService;

import fontys.s3.carspacebackend.configuration.security.isauthenticated.IsAuthenticated;
import fontys.s3.carspacebackend.controller.responses.*;
import fontys.s3.carspacebackend.converters.AuctionConverter;
import fontys.s3.carspacebackend.domain.Auction;
import fontys.s3.carspacebackend.controller.dto.AuctionDTO;
import fontys.s3.carspacebackend.controller.requests.CreateAuctionReq;

import fontys.s3.carspacebackend.domain.AuctionFilters;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @GetMapping()
    public ResponseEntity<GenericObjectResponse> getAuctionsByCreator(@RequestParam(required = true) Long creatorId){
        List<Auction> auctions = auctionService.getAuctionsByCreator(creatorId);
        List<AuctionDTO> dtos = auctions.stream().map(AuctionConverter::convertToDTO).collect(Collectors.toList());
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

    @GetMapping("/filter")
    public ResponseEntity<FilteredAuctionsResponse> getLiveAuctions(
            @RequestParam(defaultValue = "", required = false) String carBrand,
            @RequestParam(defaultValue = "", required = false) String carModel,
            @RequestParam(defaultValue = "", required = false) String location,
            @RequestParam(defaultValue = "1980", required = false) int minYear,
            @RequestParam(defaultValue = "2030", required = false) int maxYear,
            @RequestParam(defaultValue = "0.0", required = false) double minPrice,
            @RequestParam(defaultValue = "214748364.0", required = false) double maxPrice,
            @RequestParam(defaultValue = "0", required = false) int minMileage,
            @RequestParam(defaultValue = "2000000", required = false) int maxMileage,
            @RequestParam(defaultValue = "false", required = false) boolean hasEnded,
            @RequestParam(defaultValue = "0") int page)
    {
        Pageable paging = PageRequest.of(page, 1);
        AuctionFilters filters = AuctionFilters.builder().carBrand(carBrand).carModel(carModel).minYear(minYear).maxYear(maxYear).location(location).minPrice(minPrice).maxPrice(maxPrice).minMileage(minMileage).maxMileage(maxMileage).hasEnded(hasEnded).build();
        Page<Auction> pages = auctionService.getLiveAuctions(filters, paging);
        List<AuctionDTO> dtos = pages.getContent().stream().map(a -> AuctionConverter.convertToDTO(a)).collect(Collectors.toList());
        FilteredAuctionsResponse res = FilteredAuctionsResponse.builder().auctions(dtos).currentPage(pages.getNumber()).totalItems(pages.getTotalElements()).totalPages(pages.getTotalPages()).build();
        return ResponseEntity.ok(res);
    }
}
