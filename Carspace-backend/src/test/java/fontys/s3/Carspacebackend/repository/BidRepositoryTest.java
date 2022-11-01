package fontys.s3.Carspacebackend.repository;

import fontys.s3.Carspacebackend.domain.Auction;
import fontys.s3.Carspacebackend.domain.Bid;
import fontys.s3.Carspacebackend.domain.Image;
import fontys.s3.Carspacebackend.persistence.repository.impl.AuctionRepository;
import fontys.s3.Carspacebackend.persistence.repository.impl.BidRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;


import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BidRepositoryTest extends RepositoryTest{
    @Autowired
    private BidRepository bidRepo;
    @Autowired
    private AuctionRepository auctionRepo;

    @AfterEach
    void tearDown()
    {
        CleanDB();
    }

    @Transactional
    @Test
    void shouldSaveBidAndAppearInGetAuction() {
        InsertTestAuctions();
        Bid savedBid = Bid.builder().amount(500.0).build();

        Long bidId = bidRepo.saveBid(savedBid, 1L, 3L);
        assertNotNull(bidId);

        Auction auction = auctionRepo.getAuctionById(1L);

        //Java is autistic and hashset has no get method, so this will have to do
        for (Bid b :auction.getBids()) {
            assertEquals(bidId, b.getId());
            assertEquals(savedBid.getAmount(), b.getAmount());
            assertEquals(3L, b.getBidder().getId());
            assertTrue(b.getCreatedOn().isBefore(Instant.now()));
        }
    }
}
