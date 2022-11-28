package fontys.s3.carspacebackend.validator;

import fontys.s3.carspacebackend.business.validator.IAuctionValidator;
import fontys.s3.carspacebackend.domain.Auction;
import fontys.s3.carspacebackend.exception.IncorrectAuctionDates;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class AuctionValidatorTest {
    @Autowired
    private IAuctionValidator auctionValidator;

    @Test
    void testThrowExceptionWhenDatesAreInPast(){

        Instant now = Instant.now();
        Instant auctionStart = now.minus(2, ChronoUnit.DAYS);
        Instant auctionEnd = now.minus(3, ChronoUnit.DAYS);

        Auction a = Auction.builder().id(100L).carBrand("BMW").carModel("330i").carDesc("desc").carYear(2002).startingPrice(1000).buyoutPrice(2000).hasSold(true).location("123 avenue").startsOn(auctionStart).endsOn(auctionEnd).build();

        Exception exception = assertThrows(IncorrectAuctionDates.class, () -> {
            auctionValidator.ValidateDatesForModification(a);
        });

        String expectedMessage = "Dates cannot be in the past";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testThrowExceptionWhenEndDateIsBeforeStartDate(){

        Instant now = Instant.now();
        Instant auctionStart = now.plus(2, ChronoUnit.DAYS);
        Instant auctionEnd = now.plus(1, ChronoUnit.DAYS);

        Auction a = Auction.builder().id(100L).carBrand("BMW").carModel("330i").carDesc("desc").carYear(2002).startingPrice(1000).buyoutPrice(2000).hasSold(true).location("123 avenue").startsOn(auctionStart).endsOn(auctionEnd).build();

        Exception exception = assertThrows(IncorrectAuctionDates.class, () -> {
            auctionValidator.ValidateDatesForModification(a);
        });

        String expectedMessage = "End date is before start date";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testThrowExceptionWhenStartDateIsTooSoon(){

        Instant now = Instant.now();
        Instant auctionStart = now.plus(30, ChronoUnit.MINUTES);
        Instant auctionEnd = now.plus(2, ChronoUnit.DAYS);

        Auction a = Auction.builder().id(100L).carBrand("BMW").carModel("330i").carDesc("desc").carYear(2002).startingPrice(1000).buyoutPrice(2000).hasSold(true).location("123 avenue").startsOn(auctionStart).endsOn(auctionEnd).build();

        Exception exception = assertThrows(IncorrectAuctionDates.class, () -> {
            auctionValidator.ValidateDatesForModification(a);
        });

        String expectedMessage = "Auction must start at least 1 day after today";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testThrowExceptionWhenAuctionRuntimeIsLessThanOneDay(){

        Instant now = Instant.now();
        Instant auctionStart = now.plus(2, ChronoUnit.DAYS);
        Instant auctionEnd = now.plus(2, ChronoUnit.DAYS).plus(30, ChronoUnit.MINUTES);

        Auction a = Auction.builder().id(100L).carBrand("BMW").carModel("330i").carDesc("desc").carYear(2002).startingPrice(1000).buyoutPrice(2000).hasSold(true).location("123 avenue").startsOn(auctionStart).endsOn(auctionEnd).build();

        Exception exception = assertThrows(IncorrectAuctionDates.class, () -> {
            auctionValidator.ValidateDatesForModification(a);
        });

        String expectedMessage = "Auction must run for at least 1 day";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testValidationDatesSuccessful(){

        Instant now = Instant.now();
        Instant auctionStart = now.plus(1, ChronoUnit.DAYS).plus(30, ChronoUnit.MINUTES);
        Instant auctionEnd = now.plus(5, ChronoUnit.DAYS);

        Auction a = Auction.builder().id(100L).carBrand("BMW").carModel("330i").carDesc("desc").carYear(2002).startingPrice(1000).buyoutPrice(2000).hasSold(true).location("123 avenue").startsOn(auctionStart).endsOn(auctionEnd).build();

        assertTrue(auctionValidator.ValidateDatesForModification(a));
    }
}
