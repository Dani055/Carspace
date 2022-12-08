package fontys.s3.carspacebackend.repository;

import fontys.s3.carspacebackend.domain.*;
import fontys.s3.carspacebackend.exception.ResourceNotFoundException;
import fontys.s3.carspacebackend.persistence.repository.impl.AuctionRepository;
import fontys.s3.carspacebackend.persistence.repository.impl.BidRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuctionRepositoryTest extends RepositoryTest{
    @Autowired
    private AuctionRepository auctionRepo;

    @Autowired
    private BidRepository bidRepo;

    @AfterEach
    void tearDown()
    {
        CleanDB();
    }
    @Transactional
    @Test
    void shouldSaveAuctionAndGetAuction() {
        //AUTOMATICALLY TESTS IMAGE REPO
        InsertTestUsers();
        List<String> urls = new ArrayList<>();
        urls.add("img1");
        urls.add("img2");

        Instant aucStart = TimeHelper.Now().plus(1, ChronoUnit.DAYS);
        Instant aucEnd = TimeHelper.Now().plus(4, ChronoUnit.DAYS);
        Auction savedAuction = Auction.builder().carBrand("BMW").carModel("330i").carDesc("desc").carYear(2002).startingPrice(1000).buyoutPrice(2000).mileage(10000).hasSold(false).location("123 avenue").startsOn(aucStart).endsOn(aucEnd).build();
        Long auctionId = auctionRepo.saveAuction(savedAuction, 2L, urls);
        assertNotNull(auctionId);

        Auction foundAuction = auctionRepo.getAuctionById(auctionId);

        assertEquals(auctionId, foundAuction.getId());
        assertEquals(savedAuction.getCarBrand(), foundAuction.getCarBrand());
        assertEquals(savedAuction.getCarDesc(), foundAuction.getCarDesc());
        assertEquals(savedAuction.getCarYear(), foundAuction.getCarYear());
        assertEquals(savedAuction.getStartingPrice(), foundAuction.getStartingPrice());
        assertEquals(savedAuction.getBuyoutPrice(), foundAuction.getBuyoutPrice());
        assertEquals(savedAuction.getMileage(), foundAuction.getMileage());
        assertFalse(foundAuction.isHasSold());
        assertEquals(savedAuction.getLocation(), foundAuction.getLocation());
        assertEquals(savedAuction.getStartsOn(), foundAuction.getStartsOn());
        assertEquals(savedAuction.getEndsOn(), foundAuction.getEndsOn());
        assertEquals(2L, foundAuction.getCreator().getId());

        int currentIndex = 0;
        for (Image img :foundAuction.getImages()) {

            assertEquals(img.getImgUrl(), urls.get(currentIndex));
            currentIndex++;
        }

        assertEquals(0, foundAuction.getBids().size());
        assertEquals(0, foundAuction.getComments().size());
        assertNull(foundAuction.getWinningBid());
    }

    @Transactional
    @Test
    void shouldSaveAuctionAndGetAuctionsByCreator() {
        InsertTestAuctions();
        List<String> urls = new ArrayList<>();


        Instant aucStart = TimeHelper.Now().plus(1, ChronoUnit.DAYS);
        Instant aucEnd = TimeHelper.Now().plus(4, ChronoUnit.DAYS);
        Auction savedAuction = Auction.builder().carBrand("BMW").carModel("330d").carDesc("desc").carYear(2002).startingPrice(1000).buyoutPrice(2000).mileage(10000).hasSold(false).location("123 avenue").startsOn(aucStart).endsOn(aucEnd).build();
        Long auctionId = auctionRepo.saveAuction(savedAuction, 2L, urls);
        assertNotNull(auctionId);

        List<Auction> foundAuctions = auctionRepo.getAuctionsByCreator(2L);

        assertEquals(3, foundAuctions.size());
        int currentIndex = 1;
        for (Auction a :foundAuctions) {
            assertEquals(a.getId(), currentIndex);
            currentIndex++;
        }
    }

    @Transactional
    @Test
    void shouldSaveAuctionsAndGetLiveAuctionsByFilters() {
        InsertTestAuctions();
        List<String> urls = new ArrayList<>();


        Instant aucStart = TimeHelper.Now().plus(1, ChronoUnit.DAYS);
        Instant aucEnd = TimeHelper.Now().plus(4, ChronoUnit.DAYS);
        Auction savedAuction = Auction.builder().carBrand("Mercedes").carModel("300e").carDesc("desc").carYear(2002).startingPrice(1000).buyoutPrice(2000).mileage(10000).hasSold(false).location("123 avenue").startsOn(aucStart).endsOn(aucEnd).build();
        Long auctionId = auctionRepo.saveAuction(savedAuction, 2L, urls);
        assertNotNull(auctionId);

        Pageable paging = PageRequest.of(0, 1);
        AuctionFilters filters = AuctionFilters.builder().carBrand("Merc").carModel("").minYear(1980).maxYear(2200).location("").minPrice(0).maxPrice(200000).minMileage(0).maxMileage(1000000).hasEnded(false).build();
        Page<Auction> foundAuctions = auctionRepo.findLiveAuctionsByFilters(filters, paging);

        assertEquals(2, foundAuctions.getTotalElements());
        Auction a = foundAuctions.getContent().get(0);
        assertEquals(2, a.getId());
    }
    @Transactional
    @Test
    void shouldSaveAuctionsAndGetEndedAuctionsByFilters() {
        InsertTestAuctions();
        List<String> urls = new ArrayList<>();


        Instant aucStart = TimeHelper.Now().minus(5, ChronoUnit.DAYS);
        Instant aucEnd = TimeHelper.Now().minus(2, ChronoUnit.DAYS);
        Auction endedAuction = Auction.builder().carBrand("Mercedes").carModel("300e").carDesc("desc").carYear(2002).startingPrice(1000).buyoutPrice(2000).mileage(10000).hasSold(false).location("123 avenue").startsOn(aucStart).endsOn(aucEnd).build();
        Long auctionId = auctionRepo.saveAuction(endedAuction, 2L, urls);
        assertNotNull(auctionId);

        Pageable paging = PageRequest.of(0, 1);
        AuctionFilters filters = AuctionFilters.builder().carBrand("Merc").carModel("").minYear(1980).maxYear(2200).location("").minPrice(0).maxPrice(200000).minMileage(0).maxMileage(1000000).hasEnded(false).build();
        Page<Auction> foundAuctions = auctionRepo.findLiveAuctionsByFilters(filters, paging);

        assertEquals(2, foundAuctions.getTotalElements());
        Auction a = foundAuctions.getContent().get(0);
        assertEquals(2, a.getId());
    }
    @Transactional
    @Test
    void shouldSaveAuctionAndEditInfoAndGet() {
        InsertTestUsers();
        List<String> urls = new ArrayList<>();
        urls.add("img1");
        urls.add("img2");

        Instant aucStart = TimeHelper.Now().plus(1, ChronoUnit.DAYS);
        Instant aucEnd = TimeHelper.Now().plus(4, ChronoUnit.DAYS);
        Auction savedAuction = Auction.builder().carBrand("BMW").carModel("330i").carDesc("desc").carYear(2002).startingPrice(1000).buyoutPrice(2000).mileage(10000).hasSold(false).location("123 avenue").startsOn(aucStart).endsOn(aucEnd).build();
        Long auctionId = auctionRepo.saveAuction(savedAuction, 2L, urls);
        assertNotNull(auctionId);

        List<String> newUrls = new ArrayList<>();
        urls.add("newImage1");
        urls.add("newImage2");
        Auction editedAuction = Auction.builder().id(auctionId).carBrand("Merc").carModel("300E").carDesc("new desc").carYear(1999).startingPrice(950).buyoutPrice(3000).mileage(200000).hasSold(true).location("Home").startsOn(aucStart.plus(1, ChronoUnit.DAYS)).endsOn(aucEnd.plus(1, ChronoUnit.DAYS)).build();
        auctionRepo.changeAuctionInfo(editedAuction, newUrls);

        Auction foundAuction = auctionRepo.getAuctionById(auctionId);

        assertEquals(auctionId, foundAuction.getId());
        assertEquals(editedAuction.getCarBrand(), foundAuction.getCarBrand());
        assertEquals(editedAuction.getCarDesc(), foundAuction.getCarDesc());
        assertEquals(editedAuction.getCarYear(), foundAuction.getCarYear());
        assertEquals(editedAuction.getStartingPrice(), foundAuction.getStartingPrice());
        assertEquals(editedAuction.getBuyoutPrice(), foundAuction.getBuyoutPrice());
        assertEquals(editedAuction.getMileage(), foundAuction.getMileage());
        assertFalse(foundAuction.isHasSold());
        assertEquals(editedAuction.getLocation(), foundAuction.getLocation());
        assertEquals(editedAuction.getStartsOn(), foundAuction.getStartsOn());
        assertEquals(editedAuction.getEndsOn(), foundAuction.getEndsOn());
        assertEquals(2L, foundAuction.getCreator().getId());

        int currentIndex = 0;
        for (Image img :foundAuction.getImages()) {

            assertEquals(img.getImgUrl(), newUrls.get(currentIndex));
            currentIndex++;
        }

        assertEquals(0, foundAuction.getBids().size());
        assertEquals(0, foundAuction.getComments().size());
        assertNull(foundAuction.getWinningBid());
    }

    @Transactional
    @Test
    void shouldSaveAuctionAndDeleteAndGet() {
        InsertTestUsers();
        List<String> urls = new ArrayList<>();

        Instant aucStart = TimeHelper.Now().plus(1, ChronoUnit.DAYS);
        Instant aucEnd = TimeHelper.Now().plus(4, ChronoUnit.DAYS);
        Auction savedAuction = Auction.builder().carBrand("BMW").carModel("330i").carDesc("desc").carYear(2002).startingPrice(1000).buyoutPrice(2000).mileage(10000).hasSold(false).location("123 avenue").startsOn(aucStart).endsOn(aucEnd).build();
        Long auctionId = auctionRepo.saveAuction(savedAuction, 2L, urls);
        assertNotNull(auctionId);

        auctionRepo.deleteAuction(auctionId);



        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            Auction foundAuction = auctionRepo.getAuctionById(auctionId);
        });

        String expectedMessage = "Auction not found with id : " + "'" + auctionId + "'";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}
