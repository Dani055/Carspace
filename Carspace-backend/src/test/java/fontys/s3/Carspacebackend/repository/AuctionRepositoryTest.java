package fontys.s3.Carspacebackend.repository;

import fontys.s3.Carspacebackend.domain.*;
import fontys.s3.Carspacebackend.exception.AuctionHasStartedException;
import fontys.s3.Carspacebackend.exception.ResourceNotFoundException;
import fontys.s3.Carspacebackend.persistence.Entity.ImageEntity;
import fontys.s3.Carspacebackend.persistence.repository.impl.AuctionImageRepository;
import fontys.s3.Carspacebackend.persistence.repository.impl.AuctionRepository;
import fontys.s3.Carspacebackend.persistence.repository.impl.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
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
public class AuctionRepositoryTest extends RepositoryTest{
    @Autowired
    private AuctionRepository auctionRepo;

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
