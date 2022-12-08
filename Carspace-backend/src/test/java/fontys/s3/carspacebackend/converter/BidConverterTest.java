package fontys.s3.carspacebackend.converter;

import fontys.s3.carspacebackend.controller.dto.BidDTO;
import fontys.s3.carspacebackend.controller.dto.UserDTO;
import fontys.s3.carspacebackend.persistence.entity.converters.BidConverter;
import fontys.s3.carspacebackend.domain.Bid;
import fontys.s3.carspacebackend.domain.User;
import fontys.s3.carspacebackend.domain.impl.UserRole;
import fontys.s3.carspacebackend.persistence.entity.BidEntity;
import fontys.s3.carspacebackend.persistence.entity.RoleEntity;
import fontys.s3.carspacebackend.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import static org.junit.jupiter.api.Assertions.*;

class BidConverterTest {
    @Test
    void testConvertToPOJO(){
        Instant now = Instant.now();
        RoleEntity roleE = RoleEntity.builder().id(99L).roleName("user").build();
        UserEntity userE = UserEntity.builder().id(1L).role(roleE).username("jdoe").password("pass").firstName("john").lastName("doe").email("jdoe@gmail.com").address("avenue 123").phone("+311").build();

        BidEntity bidEntity = BidEntity.builder().id(2L).amount(500.4).createdOn(now).bidder(userE).build();
        Bid b = BidConverter.convertToPOJO(bidEntity);

        assertEquals(2L, b.getId());
        assertEquals(500.4, b.getAmount());
        assertEquals(now, b.getCreatedOn());
        assertTrue(b.getBidder() instanceof User);
    }

    @Test
    void testConvertToPOJO_null(){
        BidEntity bidEntity = null;
        Bid b = BidConverter.convertToPOJO(bidEntity);

        assertNull(b);
    }

    @Test
    void testConvertToEntity(){
        Instant now = Instant.now();

        UserRole role = UserRole.builder().id(99L).role("user").build();
        User user = User.builder().id(1L).role(role).username("jdoe").password("pass").firstName("john").lastName("doe").email("jdoe@gmail.com").address("avenue 123").phone("+311").build();

        Bid b = Bid.builder().id(2L).amount(500.4).createdOn(now).bidder(user).build();
        BidEntity bidE = BidConverter.convertToEntity(b);

        assertEquals(2L, bidE.getId());
        assertEquals(500.4, bidE.getAmount());
        assertEquals(now, bidE.getCreatedOn());
        assertTrue(bidE.getBidder() instanceof UserEntity);
    }

    @Test
    void testConvertToEntity_null(){
        Bid b = null;
        BidEntity bidE = BidConverter.convertToEntity(b);

        assertNull(bidE);
    }

    @Test
    void testConvertToDTO(){
        Instant now = Instant.now();

        UserRole role = UserRole.builder().id(99L).role("user").build();
        User user = User.builder().id(1L).role(role).username("jdoe").password("pass").firstName("john").lastName("doe").email("jdoe@gmail.com").address("avenue 123").phone("+311").build();

        Bid b = Bid.builder().id(2L).amount(500.4).createdOn(now).bidder(user).build();
        BidDTO dto = BidConverter.convertToDTO(b);

        assertEquals(2L, dto.getId());
        assertEquals(500.4, dto.getAmount());
        assertEquals(now, dto.getCreatedOn());
        assertTrue(dto.getBidder() instanceof UserDTO);
    }

    @Test
    void testConvertToDTO_null(){
        Bid b = null;
        BidDTO dto = BidConverter.convertToDTO(b);

        assertNull(dto);
    }
}
