package fontys.s3.Carspacebackend.service;

import fontys.s3.Carspacebackend.business.service.impl.BidService;
import fontys.s3.Carspacebackend.domain.Auction;
import fontys.s3.Carspacebackend.domain.Bid;
import fontys.s3.Carspacebackend.domain.TimeHelper;
import fontys.s3.Carspacebackend.domain.User;
import fontys.s3.Carspacebackend.exception.CannotPlaceBidException;
import fontys.s3.Carspacebackend.persistence.repository.impl.AuctionRepository;
import fontys.s3.Carspacebackend.persistence.repository.impl.BidRepository;
import fontys.s3.Carspacebackend.persistence.repository.impl.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.Instant;
import java.util.HashSet;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BidServiceTest {
    @Mock
    private AuctionRepository auctionRepoMock;

    @Mock
    private UserRepository userRepoMock;
    @Mock
    private BidRepository bidRepoMock;

    @InjectMocks
    private BidService bidService;

    @Test
    void tryPlaceBidWhenAuctionHasNotStarted(){
        TimeHelper.EnterDebugMode();
        Instant aucStart = Instant.parse("2022-07-12T15:30:00.00Z");
        User aucCreator = User.builder().id(50L).username("jdoe").password("pass").firstName("john").lastName("doe").email("jdoe@gmail.com").address("avenue 123").phone("+311").build();
        Auction auction = Auction.builder().id(100L).creator(aucCreator).carBrand("BMW").carModel("330i").carDesc("desc").carYear(2002).startingPrice(1000).buyoutPrice(2000).hasSold(false).startsOn(aucStart).build();
        Bid toCreate = Bid.builder().id(25L).amount(500.0).build();

        when(userRepoMock.findById(aucCreator.getId())).thenReturn(aucCreator);
        when(auctionRepoMock.getAuctionById(auction.getId())).thenReturn(auction);

        Exception exception = assertThrows(CannotPlaceBidException.class, () -> {
            bidService.createBid(toCreate, auction.getId(), aucCreator.getId());
        });

        String expectedMessage = "Cannot place bid. Reason: Auction has not started yet";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(userRepoMock).findById(50L);
        verify(auctionRepoMock).getAuctionById(100L);
        TimeHelper.QuitDebugMode();
    }

    @Test
    void tryPlaceBidWhenAuctionHasEnded(){
        TimeHelper.EnterDebugMode();
        Instant aucStart = Instant.parse("2022-07-08T15:30:00.00Z");
        Instant aucEnd = Instant.parse("2022-07-09T15:30:00.00Z");

        User aucCreator = User.builder().id(50L).username("jdoe").password("pass").firstName("john").lastName("doe").email("jdoe@gmail.com").address("avenue 123").phone("+311").build();
        Auction auction = Auction.builder().id(100L).creator(aucCreator).carBrand("BMW").carModel("330i").carDesc("desc").carYear(2002).startingPrice(1000).buyoutPrice(2000).hasSold(false).startsOn(aucStart).endsOn(aucEnd).build();
        Bid toCreate = Bid.builder().id(25L).amount(500.0).build();

        when(userRepoMock.findById(aucCreator.getId())).thenReturn(aucCreator);
        when(auctionRepoMock.getAuctionById(auction.getId())).thenReturn(auction);

        Exception exception = assertThrows(CannotPlaceBidException.class, () -> {
            bidService.createBid(toCreate, auction.getId(), aucCreator.getId());
        });

        String expectedMessage = "Cannot place bid. Reason: Auction has ended";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(userRepoMock).findById(50L);
        verify(auctionRepoMock).getAuctionById(100L);
        TimeHelper.QuitDebugMode();
    }

    @Test
    void tryPlaceBidOnOwnAuction(){
        TimeHelper.EnterDebugMode();
        Instant aucStart = Instant.parse("2022-07-08T15:30:00.00Z");
        Instant aucEnd = Instant.parse("2022-07-12T15:30:00.00Z");

        User aucCreator = User.builder().id(50L).username("jdoe").password("pass").firstName("john").lastName("doe").email("jdoe@gmail.com").address("avenue 123").phone("+311").build();
        Auction auction = Auction.builder().id(100L).creator(aucCreator).carBrand("BMW").carModel("330i").carDesc("desc").carYear(2002).startingPrice(1000).buyoutPrice(2000).hasSold(false).startsOn(aucStart).endsOn(aucEnd).build();
        Bid toCreate = Bid.builder().id(25L).amount(500.0).build();

        when(userRepoMock.findById(aucCreator.getId())).thenReturn(aucCreator);
        when(auctionRepoMock.getAuctionById(auction.getId())).thenReturn(auction);

        Exception exception = assertThrows(CannotPlaceBidException.class, () -> {
            bidService.createBid(toCreate, auction.getId(), aucCreator.getId());
        });

        String expectedMessage = "Cannot place bid. Reason: You cannot bid on your own auction";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(userRepoMock).findById(50L);
        verify(auctionRepoMock).getAuctionById(100L);
        TimeHelper.QuitDebugMode();
    }

    @Test
    void tryPlaceBidWhenAmountIsBelowMinimum(){
        TimeHelper.EnterDebugMode();
        Instant aucStart = Instant.parse("2022-07-08T15:30:00.00Z");
        Instant aucEnd = Instant.parse("2022-07-12T15:30:00.00Z");

        User aucCreator = User.builder().id(50L).username("jdoe").password("pass").firstName("john").lastName("doe").email("jdoe@gmail.com").address("avenue 123").phone("+311").build();
        User bidder = User.builder().id(55L).username("jdoe").password("pass").firstName("john").lastName("doe").email("jdoe@gmail.com").address("avenue 123").phone("+311").build();
        Auction auction = Auction.builder().id(100L).creator(aucCreator).carBrand("BMW").carModel("330i").carDesc("desc").carYear(2002).startingPrice(1000).buyoutPrice(2000).hasSold(false).startsOn(aucStart).endsOn(aucEnd).build();
        Bid toCreate = Bid.builder().id(25L).amount(500.0).bidder(bidder).build();

        when(userRepoMock.findById(bidder.getId())).thenReturn(bidder);
        when(auctionRepoMock.getAuctionById(auction.getId())).thenReturn(auction);

        Exception exception = assertThrows(CannotPlaceBidException.class, () -> {
            bidService.createBid(toCreate, auction.getId(), bidder.getId());
        });

        String expectedMessage = "Cannot place bid. Reason: Bid is not above the minimum amount";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(userRepoMock).findById(55L);
        verify(auctionRepoMock).getAuctionById(100L);
        TimeHelper.QuitDebugMode();
    }

    @Test
    void tryPlaceBidWhenAmountIsAboveMaximum(){
        TimeHelper.EnterDebugMode();
        Instant aucStart = Instant.parse("2022-07-08T15:30:00.00Z");
        Instant aucEnd = Instant.parse("2022-07-12T15:30:00.00Z");

        User aucCreator = User.builder().id(50L).username("jdoe").password("pass").firstName("john").lastName("doe").email("jdoe@gmail.com").address("avenue 123").phone("+311").build();
        User bidder = User.builder().id(55L).username("jdoe").password("pass").firstName("john").lastName("doe").email("jdoe@gmail.com").address("avenue 123").phone("+311").build();
        Auction auction = Auction.builder().id(100L).creator(aucCreator).carBrand("BMW").carModel("330i").carDesc("desc").carYear(2002).startingPrice(1000).buyoutPrice(2000).hasSold(false).startsOn(aucStart).endsOn(aucEnd).build();
        Bid toCreate = Bid.builder().id(25L).amount(2500.0).bidder(bidder).build();

        when(userRepoMock.findById(bidder.getId())).thenReturn(bidder);
        when(auctionRepoMock.getAuctionById(auction.getId())).thenReturn(auction);

        Exception exception = assertThrows(CannotPlaceBidException.class, () -> {
            bidService.createBid(toCreate, auction.getId(), bidder.getId());
        });

        String expectedMessage = "Cannot place bid. Reason: Bid is above the buyout amount";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(userRepoMock).findById(55L);
        verify(auctionRepoMock).getAuctionById(100L);
        TimeHelper.QuitDebugMode();
    }

    @Test
    void tryPlaceBidWhenBelowHighest(){
        TimeHelper.EnterDebugMode();
        Instant aucStart = Instant.parse("2022-07-08T15:30:00.00Z");
        Instant aucEnd = Instant.parse("2022-07-12T15:30:00.00Z");

        User aucCreator = User.builder().id(50L).username("jdoe").password("pass").firstName("john").lastName("doe").email("jdoe@gmail.com").address("avenue 123").phone("+311").build();
        User bidder = User.builder().id(55L).username("jdoe").password("pass").firstName("john").lastName("doe").email("jdoe@gmail.com").address("avenue 123").phone("+311").build();


        Bid toCreate = Bid.builder().id(25L).amount(1000.0).bidder(bidder).build();
        HashSet<Bid> bids = new HashSet<>();
        bids.add(Bid.builder().id(24L).amount(1500.0).build());
        bids.add(Bid.builder().id(23L).amount(1200.0).build());
        Auction auction = Auction.builder().id(100L).creator(aucCreator).carBrand("BMW").carModel("330i").bids(bids).carYear(2002).startingPrice(1000).buyoutPrice(2000).hasSold(false).startsOn(aucStart).endsOn(aucEnd).build();

        when(userRepoMock.findById(bidder.getId())).thenReturn(bidder);
        when(auctionRepoMock.getAuctionById(auction.getId())).thenReturn(auction);

        Exception exception = assertThrows(CannotPlaceBidException.class, () -> {
            bidService.createBid(toCreate, auction.getId(), bidder.getId());
        });

        String expectedMessage = "Cannot place bid. Reason: Bid is less than the highest bid";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(userRepoMock).findById(55L);
        verify(auctionRepoMock).getAuctionById(100L);
        TimeHelper.QuitDebugMode();
    }

    @Test
    void placeBid(){
        TimeHelper.EnterDebugMode();
        Instant aucStart = Instant.parse("2022-07-08T15:30:00.00Z");
        Instant aucEnd = Instant.parse("2022-07-12T15:30:00.00Z");

        User aucCreator = User.builder().id(50L).username("jdoe").password("pass").firstName("john").lastName("doe").email("jdoe@gmail.com").address("avenue 123").phone("+311").build();
        User bidder = User.builder().id(55L).username("jdoe").password("pass").firstName("john").lastName("doe").email("jdoe@gmail.com").address("avenue 123").phone("+311").build();


        Bid toCreate = Bid.builder().id(25L).amount(1600.0).bidder(bidder).build();
        HashSet<Bid> bids = new HashSet<>();
        bids.add(Bid.builder().id(24L).amount(1500.0).build());
        bids.add(Bid.builder().id(23L).amount(1200.0).build());
        Auction auction = Auction.builder().id(100L).creator(aucCreator).carBrand("BMW").carModel("330i").bids(bids).carYear(2002).startingPrice(1000).buyoutPrice(2000).hasSold(false).startsOn(aucStart).endsOn(aucEnd).build();

        when(userRepoMock.findById(bidder.getId())).thenReturn(bidder);
        when(auctionRepoMock.getAuctionById(auction.getId())).thenReturn(auction);
        when(bidRepoMock.saveBid(toCreate, auction.getId(), bidder.getId())).thenReturn(123L);

        assertEquals(123L, bidService.createBid(toCreate, auction.getId(), bidder.getId()));
        verify(userRepoMock).findById(55L);
        verify(auctionRepoMock).getAuctionById(100L);
        verify(bidRepoMock).saveBid(toCreate, 100L, 55L);
        TimeHelper.QuitDebugMode();
    }
}
