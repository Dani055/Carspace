package fontys.s3.carspacebackend.service;

import fontys.s3.carspacebackend.business.service.impl.AuctionService;
import fontys.s3.carspacebackend.business.validator.AuctionValidator;
import fontys.s3.carspacebackend.domain.AccessToken;
import fontys.s3.carspacebackend.domain.Auction;
import fontys.s3.carspacebackend.domain.TimeHelper;
import fontys.s3.carspacebackend.domain.User;
import fontys.s3.carspacebackend.domain.impl.AdminRole;
import fontys.s3.carspacebackend.domain.impl.UserRole;
import fontys.s3.carspacebackend.exception.AuctionHasStartedException;
import fontys.s3.carspacebackend.exception.UnauthorizedException;
import fontys.s3.carspacebackend.persistence.repository.impl.AuctionRepository;
import fontys.s3.carspacebackend.persistence.repository.impl.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuctionServiceTest {
    @Mock
    private AuctionRepository auctionRepoMock;

    @Mock
    private UserRepository userRepoMock;
    @Mock
    private AuctionValidator validatorMock;

    @Mock
    private AccessToken accessToken;
    @InjectMocks
    private AuctionService auctionService;

    @Test
    void createAuction() {
        Long userId = 1L;
        List<String> urls = new ArrayList<>();
        urls.add("img1");
        Auction toCreate = Auction.builder().id(100L).carBrand("BMW").carModel("330i").carDesc("desc").carYear(2002).startingPrice(1000).buyoutPrice(2000).hasSold(true).location("123 avenue").build();

        when(validatorMock.ValidateDatesForModification(toCreate)).thenReturn(true);
        when(auctionRepoMock.saveAuction(toCreate, userId, urls)).thenReturn(200L);
        when(accessToken.getUserId()).thenReturn(userId);

        assertEquals(200L, auctionService.createAuction(toCreate, urls));
        verify(validatorMock).ValidateDatesForModification(toCreate);
        verify(auctionRepoMock).saveAuction(toCreate, userId, urls);
        verify(accessToken).getUserId();
    }

    @Test
    void tryEditAuctionWhenNotOwner() {
        UserRole role = UserRole.builder().id(1L).role("user").build();
        User aucCreator = User.builder().id(50L).username("jdoe").role(role).password("pass").firstName("john").lastName("doe").email("jdoe@gmail.com").address("avenue 123").phone("+311").build();
        User differentUser = User.builder().id(65L).username("jdoe").role(role).password("pass").build();
        List<String> urls = new ArrayList<>();
        urls.add("img1");
        Auction toEdit = Auction.builder().id(100L).creator(aucCreator).carBrand("BMW").carModel("330i").carDesc("desc").carYear(2002).startingPrice(1000).buyoutPrice(2000).hasSold(false).location("123 avenue").build();

        when(accessToken.getUserId()).thenReturn(65L);
        when(userRepoMock.findById(65L)).thenReturn(differentUser);
        when(auctionRepoMock.getAuctionById(100L)).thenReturn(toEdit);

        Exception exception = assertThrows(UnauthorizedException.class, () -> {
            auctionService.editAuction(toEdit, urls);
        });

        String expectedMessage = "You are not authorized to modify resource:Auction";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(userRepoMock).findById(65L);
        verify(auctionRepoMock).getAuctionById(100L);
        verify(accessToken).getUserId();
    }

    @Test
    void tryEditAuctionWhenOwnerButHasStarted() {
        TimeHelper.EnterDebugMode();

        Instant aucStart = Instant.parse("2022-07-09T15:30:00.00Z");
        UserRole role = UserRole.builder().id(1L).role("user").build();
        User aucCreator = User.builder().id(50L).username("jdoe").role(role).password("pass").firstName("john").lastName("doe").email("jdoe@gmail.com").address("avenue 123").phone("+311").build();
        List<String> urls = new ArrayList<>();
        urls.add("img1");
        Auction toEdit = Auction.builder().id(100L).creator(aucCreator).carBrand("BMW").carModel("330i").carDesc("desc").carYear(2002).startingPrice(1000).buyoutPrice(2000).hasSold(false).startsOn(aucStart).build();

        when(accessToken.getUserId()).thenReturn(aucCreator.getId());
        when(userRepoMock.findById(aucCreator.getId())).thenReturn(aucCreator);
        when(auctionRepoMock.getAuctionById(toEdit.getId())).thenReturn(toEdit);

        Exception exception = assertThrows(AuctionHasStartedException.class, () -> {
            auctionService.editAuction(toEdit, urls);
        });

        String expectedMessage = "Cannot change auction/delete info. It has already started";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(userRepoMock).findById(aucCreator.getId());
        verify(auctionRepoMock).getAuctionById(toEdit.getId());
        verify(accessToken).getUserId();
        TimeHelper.QuitDebugMode();
    }

    @Test
    void editAuctionWhenOwner() {
        TimeHelper.EnterDebugMode();

        Instant aucStart = Instant.parse("2022-07-11T15:30:00.00Z");
        UserRole role = UserRole.builder().id(1L).role("user").build();
        User aucCreator = User.builder().id(50L).username("jdoe").role(role).password("pass").firstName("john").lastName("doe").email("jdoe@gmail.com").address("avenue 123").phone("+311").build();
        List<String> urls = new ArrayList<>();
        Auction toEdit = Auction.builder().id(100L).creator(aucCreator).carBrand("BMW").carModel("330i").carDesc("desc").carYear(2002).startingPrice(1000).buyoutPrice(2000).hasSold(false).startsOn(aucStart).build();

        when(accessToken.getUserId()).thenReturn(aucCreator.getId());
        when(userRepoMock.findById(aucCreator.getId())).thenReturn(aucCreator);
        when(auctionRepoMock.getAuctionById(toEdit.getId())).thenReturn(toEdit);
        when(validatorMock.ValidateDatesForModification(toEdit)).thenReturn(true);
        when(auctionRepoMock.changeAuctionInfo(toEdit, urls)).thenReturn(999L);

        assertEquals(999L, auctionService.editAuction(toEdit, urls));
        verify(userRepoMock).findById(aucCreator.getId());
        verify(auctionRepoMock).getAuctionById(toEdit.getId());
        verify(validatorMock).ValidateDatesForModification(toEdit);
        verify(accessToken).getUserId();
        TimeHelper.QuitDebugMode();
    }

    @Test
    void editAuctionWhenAdminAndHasStarted() {
        TimeHelper.EnterDebugMode();

        Instant aucStart = Instant.parse("2022-07-08T15:30:00.00Z");
        AdminRole role = AdminRole.builder().id(1L).role("admin").build();
        User aucCreator = User.builder().id(50L).username("jdoe").role(role).password("pass").firstName("john").lastName("doe").email("jdoe@gmail.com").address("avenue 123").phone("+311").build();
        List<String> urls = new ArrayList<>();
        Auction toEdit = Auction.builder().id(100L).creator(aucCreator).carBrand("BMW").carModel("330i").carDesc("desc").carYear(2002).startingPrice(1000).buyoutPrice(2000).hasSold(false).startsOn(aucStart).build();

        when(accessToken.getUserId()).thenReturn(aucCreator.getId());
        when(userRepoMock.findById(aucCreator.getId())).thenReturn(aucCreator);
        when(auctionRepoMock.getAuctionById(toEdit.getId())).thenReturn(toEdit);
        when(validatorMock.ValidateDatesForModification(toEdit)).thenReturn(true);
        when(auctionRepoMock.changeAuctionInfo(toEdit, urls)).thenReturn(999L);

        assertEquals(999L, auctionService.editAuction(toEdit, urls));
        verify(userRepoMock).findById(aucCreator.getId());
        verify(auctionRepoMock).getAuctionById(toEdit.getId());
        verify(validatorMock).ValidateDatesForModification(toEdit);
        verify(accessToken).getUserId();
        TimeHelper.QuitDebugMode();
    }

    @Test
    void tryDeleteAuctionWhenNotOwner() {
        UserRole role = UserRole.builder().id(1L).role("user").build();
        User aucCreator = User.builder().id(50L).username("jdoe").role(role).password("pass").firstName("john").lastName("doe").email("jdoe@gmail.com").address("avenue 123").phone("+311").build();
        User differentUser = User.builder().id(65L).username("jdoe").role(role).password("pass").build();
        List<String> urls = new ArrayList<>();

        Auction toDelete = Auction.builder().id(100L).creator(aucCreator).carBrand("BMW").carModel("330i").carDesc("desc").carYear(2002).startingPrice(1000).buyoutPrice(2000).hasSold(false).location("123 avenue").build();

        when(accessToken.getUserId()).thenReturn(differentUser.getId());
        when(userRepoMock.findById(differentUser.getId())).thenReturn(differentUser);
        when(auctionRepoMock.getAuctionById(toDelete.getId())).thenReturn(toDelete);

        Exception exception = assertThrows(UnauthorizedException.class, () -> {
            auctionService.deleteAuction(toDelete.getId());
        });

        String expectedMessage = "You are not authorized to modify resource:Auction";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(userRepoMock).findById(65L);
        verify(auctionRepoMock).getAuctionById(100L);
        verify(accessToken).getUserId();
    }

    @Test
    void tryDeleteAuctionWhenOwnerButHasStarted() {
        TimeHelper.EnterDebugMode();

        Instant aucStart = Instant.parse("2022-07-09T15:30:00.00Z");
        UserRole role = UserRole.builder().id(1L).role("user").build();
        User aucCreator = User.builder().id(50L).username("jdoe").role(role).password("pass").firstName("john").lastName("doe").email("jdoe@gmail.com").address("avenue 123").phone("+311").build();
        List<String> urls = new ArrayList<>();

        Auction toDelete = Auction.builder().id(100L).creator(aucCreator).carBrand("BMW").carModel("330i").carDesc("desc").carYear(2002).startingPrice(1000).buyoutPrice(2000).hasSold(false).startsOn(aucStart).build();

        when(accessToken.getUserId()).thenReturn(aucCreator.getId());
        when(userRepoMock.findById(aucCreator.getId())).thenReturn(aucCreator);
        when(auctionRepoMock.getAuctionById(toDelete.getId())).thenReturn(toDelete);

        Exception exception = assertThrows(AuctionHasStartedException.class, () -> {
            auctionService.deleteAuction(toDelete.getId());
        });

        String expectedMessage = "Cannot change auction/delete info. It has already started";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(userRepoMock).findById(50L);
        verify(auctionRepoMock).getAuctionById(100L);
        verify(accessToken).getUserId();

        TimeHelper.QuitDebugMode();
    }

    @Test
    void deleteAuctionWhenOwner() {
        TimeHelper.EnterDebugMode();

        Instant aucStart = Instant.parse("2022-07-11T15:30:00.00Z");
        UserRole role = UserRole.builder().id(1L).role("user").build();
        User aucCreator = User.builder().id(50L).username("jdoe").role(role).password("pass").firstName("john").lastName("doe").email("jdoe@gmail.com").address("avenue 123").phone("+311").build();
        List<String> urls = new ArrayList<>();
        Auction toDelete = Auction.builder().id(100L).creator(aucCreator).carBrand("BMW").carModel("330i").carDesc("desc").carYear(2002).startingPrice(1000).buyoutPrice(2000).hasSold(false).startsOn(aucStart).build();

        when(accessToken.getUserId()).thenReturn(aucCreator.getId());
        when(userRepoMock.findById(aucCreator.getId())).thenReturn(aucCreator);
        when(auctionRepoMock.getAuctionById(toDelete.getId())).thenReturn(toDelete);
        when(auctionRepoMock.deleteAuction(toDelete.getId())).thenReturn(true);

        assertTrue(auctionService.deleteAuction(toDelete.getId()));
        verify(userRepoMock).findById(50L);
        verify(auctionRepoMock).getAuctionById(100L);
        verify(auctionRepoMock).deleteAuction(100L);
        verify(accessToken).getUserId();
        TimeHelper.QuitDebugMode();
    }

    @Test
    void deleteAuctionWhenAdminAndHasStarted() {
        TimeHelper.EnterDebugMode();

        Instant aucStart = Instant.parse("2022-07-08T15:30:00.00Z");
        AdminRole role = AdminRole.builder().id(1L).role("admin").build();
        User aucCreator = User.builder().id(50L).username("jdoe").role(role).password("pass").firstName("john").lastName("doe").email("jdoe@gmail.com").address("avenue 123").phone("+311").build();
        List<String> urls = new ArrayList<>();
        Auction toDelete = Auction.builder().id(100L).creator(aucCreator).carBrand("BMW").carModel("330i").carDesc("desc").carYear(2002).startingPrice(1000).buyoutPrice(2000).hasSold(false).startsOn(aucStart).build();

        when(accessToken.getUserId()).thenReturn(aucCreator.getId());
        when(userRepoMock.findById(aucCreator.getId())).thenReturn(aucCreator);
        when(auctionRepoMock.getAuctionById(toDelete.getId())).thenReturn(toDelete);
        when(auctionRepoMock.deleteAuction(toDelete.getId())).thenReturn(true);

        assertTrue(auctionService.deleteAuction(toDelete.getId()));
        verify(userRepoMock).findById(50L);
        verify(auctionRepoMock).getAuctionById(100L);
        verify(auctionRepoMock).deleteAuction(100L);
        verify(accessToken).getUserId();
        TimeHelper.QuitDebugMode();
    }
}