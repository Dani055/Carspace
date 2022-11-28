package fontys.s3.carspacebackend.converter;

import fontys.s3.carspacebackend.controller.dto.AuctionDTO;
import fontys.s3.carspacebackend.persistence.entity.converters.AuctionConverter;
import fontys.s3.carspacebackend.domain.*;
import fontys.s3.carspacebackend.domain.impl.UserRole;
import fontys.s3.carspacebackend.persistence.entity.*;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AuctionConverterTest {
    @Test
    void testConvertToPOJO(){
        Instant now = Instant.now();
        Instant auctionStart = now.minus(1, ChronoUnit.DAYS);
        Instant auctionEnd = now.plus(2, ChronoUnit.DAYS);
        RoleEntity roleE = RoleEntity.builder().id(99L).roleName("user").build();
        UserEntity userE = UserEntity.builder().id(1L).role(roleE).username("jdoe").password("pass").firstName("john").lastName("doe").email("jdoe@gmail.com").address("avenue 123").phone("+311").build();

        Set<ImageEntity> imgEntities = new HashSet<>();
        imgEntities.add(ImageEntity.builder().id(2L).imgUrl("img1").build());
        imgEntities.add(ImageEntity.builder().id(3L).imgUrl("img2").build());

        Set<BidEntity> bidEntities = new HashSet<>();
        BidEntity winningBidEntity = BidEntity.builder().id(4L).amount(500.4).createdOn(now).bidder(userE).build();
        bidEntities.add(winningBidEntity);
        bidEntities.add(BidEntity.builder().id(5L).amount(450.0).createdOn(now).bidder(userE).build());

        Set<CommentEntity> commentEntities = new HashSet<>();
        commentEntities.add(CommentEntity.builder().id(6L).text("a comment").createdOn(now).creator(userE).build());
        commentEntities.add(CommentEntity.builder().id(7L).text("comment two").createdOn(now).creator(userE).build());

        AuctionEntity ae = AuctionEntity.builder().id(100L).carBrand("BMW").carModel("330i").carDesc("desc").carYear(2002).creator(userE).startingPrice(1000).buyoutPrice(2000).hasSold(true).location("123 avenue").startsOn(auctionStart).endsOn(auctionEnd).images(imgEntities).bids(bidEntities).comments(commentEntities).winningBid(winningBidEntity).build();

        Auction a = AuctionConverter.convertToPOJO(ae);

        assertEquals(100L, a.getId());
        assertEquals("BMW", a.getCarBrand());
        assertEquals("330i", a.getCarModel());
        assertEquals("desc", a.getCarDesc());
        assertEquals(2002, a.getCarYear());
        assertEquals(1L, a.getCreator().getId());
        assertEquals(1000, a.getStartingPrice());
        assertEquals(2000, a.getBuyoutPrice());
        assertTrue(a.isHasSold());
        assertEquals(auctionStart, a.getStartsOn());
        assertEquals(auctionEnd, a.getEndsOn());
        assertEquals(2, a.getImages().size());
        assertEquals(2, a.getBids().size());
        assertEquals(2, a.getComments().size());
        assertEquals(4L, a.getWinningBid().getId());
    }

    @Test
    void testConvertToPOJO_withNullCollections(){
        Instant now = Instant.now();
        Instant auctionStart = now.minus(1, ChronoUnit.DAYS);
        Instant auctionEnd = now.plus(2, ChronoUnit.DAYS);
        RoleEntity roleE = RoleEntity.builder().id(99L).roleName("user").build();
        UserEntity userE = UserEntity.builder().id(1L).role(roleE).username("jdoe").password("pass").firstName("john").lastName("doe").email("jdoe@gmail.com").address("avenue 123").phone("+311").build();

        AuctionEntity ae = AuctionEntity.builder().id(100L).carBrand("BMW").carModel("330i").carDesc("desc").carYear(2002).creator(userE).startingPrice(1000).buyoutPrice(2000).hasSold(true).location("123 avenue").startsOn(auctionStart).endsOn(auctionEnd).build();

        Auction a = AuctionConverter.convertToPOJO(ae);

        assertEquals(100L, a.getId());
        assertEquals("BMW", a.getCarBrand());
        assertEquals("330i", a.getCarModel());
        assertEquals("desc", a.getCarDesc());
        assertEquals(2002, a.getCarYear());
        assertEquals(1L, a.getCreator().getId());
        assertEquals(1000, a.getStartingPrice());
        assertEquals(2000, a.getBuyoutPrice());
        assertTrue(a.isHasSold());
        assertEquals(auctionStart, a.getStartsOn());
        assertEquals(auctionEnd, a.getEndsOn());
        assertEquals(0, a.getImages().size());
        assertEquals(0, a.getBids().size());
        assertEquals(0, a.getComments().size());
        assertNull(a.getWinningBid());
    }

    @Test
    void testConvertToEntity(){
        Instant now = Instant.now();
        Instant auctionStart = now.minus(1, ChronoUnit.DAYS);
        Instant auctionEnd = now.plus(2, ChronoUnit.DAYS);
        UserRole role = UserRole.builder().id(99L).role("user").build();
        User user = User.builder().id(1L).role(role).username("jdoe").password("pass").firstName("john").lastName("doe").email("jdoe@gmail.com").address("avenue 123").phone("+311").build();

        Set<Image> imgs = new HashSet<>();
        imgs.add(Image.builder().id(2L).imgUrl("img1").build());
        imgs.add(Image.builder().id(3L).imgUrl("img2").build());

        Set<Bid> bids = new HashSet<>();
        Bid winningBid = Bid.builder().id(4L).amount(500.4).createdOn(now).bidder(user).build();
        bids.add(winningBid);
        bids.add(Bid.builder().id(5L).amount(450.0).createdOn(now).bidder(user).build());

        Set<Comment> comments = new HashSet<>();
        comments.add(Comment.builder().id(6L).text("a comment").createdOn(now).creator(user).build());
        comments.add(Comment.builder().id(7L).text("comment two").createdOn(now).creator(user).build());

        Auction a = Auction.builder().id(100L).carBrand("BMW").carModel("330i").carDesc("desc").carYear(2002).creator(user).startingPrice(1000).buyoutPrice(2000).hasSold(true).location("123 avenue").startsOn(auctionStart).endsOn(auctionEnd).images(imgs).bids(bids).comments(comments).winningBid(winningBid).build();

        AuctionEntity ae = AuctionConverter.convertToEntity(a);

        assertEquals(100L, ae.getId());
        assertEquals("BMW", ae.getCarBrand());
        assertEquals("330i", ae.getCarModel());
        assertEquals("desc", ae.getCarDesc());
        assertEquals(2002, ae.getCarYear());
        assertEquals(1L, ae.getCreator().getId());
        assertEquals(1000, ae.getStartingPrice());
        assertEquals(2000, ae.getBuyoutPrice());
        assertTrue(ae.isHasSold());
        assertEquals(auctionStart, ae.getStartsOn());
        assertEquals(auctionEnd, ae.getEndsOn());
        assertEquals(2, ae.getImages().size());
        assertEquals(2, ae.getBids().size());
        assertEquals(2, ae.getComments().size());
        assertEquals(4L, ae.getWinningBid().getId());
    }

    @Test
    void testConvertToDTO(){
        Instant now = Instant.now();
        Instant auctionStart = now.minus(1, ChronoUnit.DAYS);
        Instant auctionEnd = now.plus(2, ChronoUnit.DAYS);
        UserRole role = UserRole.builder().id(99L).role("user").build();
        User user = User.builder().id(1L).role(role).username("jdoe").password("pass").firstName("john").lastName("doe").email("jdoe@gmail.com").address("avenue 123").phone("+311").build();

        Set<Image> imgs = new HashSet<>();
        imgs.add(Image.builder().id(2L).imgUrl("img1").build());
        imgs.add(Image.builder().id(3L).imgUrl("img2").build());

        Set<Bid> bids = new HashSet<>();
        Bid winningBid = Bid.builder().id(4L).amount(500.4).createdOn(now).bidder(user).build();
        bids.add(winningBid);
        bids.add(Bid.builder().id(5L).amount(450.0).createdOn(now).bidder(user).build());

        Set<Comment> comments = new HashSet<>();
        comments.add(Comment.builder().id(6L).text("a comment").createdOn(now).creator(user).build());
        comments.add(Comment.builder().id(7L).text("comment two").createdOn(now).creator(user).build());

        Auction a = Auction.builder().id(100L).carBrand("BMW").carModel("330i").carDesc("desc").carYear(2002).creator(user).startingPrice(1000).buyoutPrice(2000).hasSold(true).location("123 avenue").startsOn(auctionStart).endsOn(auctionEnd).images(imgs).bids(bids).comments(comments).winningBid(winningBid).build();

        AuctionDTO dto = AuctionConverter.convertToDTO(a);

        assertEquals(100L, dto.getId());
        assertEquals("BMW", dto.getCarBrand());
        assertEquals("330i", dto.getCarModel());
        assertEquals("desc", dto.getCarDesc());
        assertEquals(2002, dto.getCarYear());
        assertEquals(1L, dto.getCreator().getId());
        assertEquals(1000, dto.getStartingPrice());
        assertEquals(2000, dto.getBuyoutPrice());
        assertTrue(dto.isHasSold());
        assertEquals(auctionStart, dto.getStartsOn());
        assertEquals(auctionEnd, dto.getEndsOn());
        assertEquals(2, dto.getImages().size());
        assertEquals(2, dto.getBids().size());
        assertEquals(2, dto.getComments().size());
        assertEquals(4L, dto.getWinningBid().getId());
    }

    @Test
    void testConvertToDTO_withNullCollections(){
        Instant now = Instant.now();
        Instant auctionStart = now.minus(1, ChronoUnit.DAYS);
        Instant auctionEnd = now.plus(2, ChronoUnit.DAYS);
        UserRole role = UserRole.builder().id(99L).role("user").build();
        User user = User.builder().id(1L).role(role).username("jdoe").password("pass").firstName("john").lastName("doe").email("jdoe@gmail.com").address("avenue 123").phone("+311").build();

        Auction a = Auction.builder().id(100L).carBrand("BMW").carModel("330i").carDesc("desc").carYear(2002).creator(user).startingPrice(1000).buyoutPrice(2000).hasSold(true).location("123 avenue").startsOn(auctionStart).endsOn(auctionEnd).build();

        AuctionDTO dto = AuctionConverter.convertToDTO(a);

        assertEquals(100L, dto.getId());
        assertEquals("BMW", dto.getCarBrand());
        assertEquals("330i", dto.getCarModel());
        assertEquals("desc", dto.getCarDesc());
        assertEquals(2002, dto.getCarYear());
        assertEquals(1L, dto.getCreator().getId());
        assertEquals(1000, dto.getStartingPrice());
        assertEquals(2000, dto.getBuyoutPrice());
        assertTrue(dto.isHasSold());
        assertEquals(auctionStart, dto.getStartsOn());
        assertEquals(auctionEnd, dto.getEndsOn());
        assertEquals(0, dto.getImages().size());
        assertEquals(0, dto.getBids().size());
        assertEquals(0, dto.getComments().size());
        assertNull(dto.getWinningBid());
    }
}
