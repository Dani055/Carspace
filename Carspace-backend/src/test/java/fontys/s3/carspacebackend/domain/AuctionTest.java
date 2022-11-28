package fontys.s3.carspacebackend.domain;

import fontys.s3.carspacebackend.domain.impl.UserRole;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AuctionTest {
    @Test
    void testIsOwner(){
        UserRole role = UserRole.builder().id(99L).role("user").build();
        User user = User.builder().id(1L).role(role).username("jdoe").password("pass").firstName("john").lastName("doe").email("jdoe@gmail.com").address("avenue 123").phone("+311").build();

        Auction a = Auction.builder().creator(user).build();

        assertTrue(a.isOwner(user));
    }
    @Test
    void testIsNotOwner(){
        UserRole role = UserRole.builder().id(99L).role("user").build();
        User owner = User.builder().id(5L).role(role).username("jdoe").password("pass").firstName("john").lastName("doe").email("jdoe@gmail.com").address("avenue 123").phone("+311").build();
        User user = User.builder().id(1L).role(role).username("jdoe").password("pass").firstName("john").lastName("doe").email("jdoe@gmail.com").address("avenue 123").phone("+311").build();

        Auction a = Auction.builder().creator(owner).build();

        assertFalse(a.isOwner(user));
    }

    @Test
    void testHasStartedAndEnded(){
        TimeHelper.EnterDebugMode();
        Instant auctionStart = Instant.parse("2022-07-05T15:30:00.00Z");
        Instant auctionEnd = Instant.parse("2022-07-10T13:30:00.00Z");
        Auction a = Auction.builder().id(100L).startsOn(auctionStart).endsOn(auctionEnd).build();

        assertTrue(a.hasStarted());
        assertTrue(a.hasEnded());
        TimeHelper.QuitDebugMode();
    }

    @Test
    void testHasNotStartedAndEnded(){
        TimeHelper.EnterDebugMode();
        Instant auctionStart = Instant.parse("2022-07-12T15:30:00.00Z");
        Instant auctionEnd = Instant.parse("2022-07-15T13:30:00.00Z");
        Auction a = Auction.builder().id(100L).startsOn(auctionStart).endsOn(auctionEnd).build();

        assertFalse(a.hasStarted());
        assertFalse(a.hasEnded());
        TimeHelper.QuitDebugMode();
    }
}
