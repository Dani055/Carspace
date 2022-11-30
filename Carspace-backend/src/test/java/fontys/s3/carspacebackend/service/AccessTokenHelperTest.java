package fontys.s3.carspacebackend.service;

import fontys.s3.carspacebackend.business.jwt.AccessTokenHelper;
import fontys.s3.carspacebackend.domain.AccessToken;
import fontys.s3.carspacebackend.domain.IRole;
import fontys.s3.carspacebackend.domain.User;
import fontys.s3.carspacebackend.domain.impl.AdminRole;
import fontys.s3.carspacebackend.exception.AuctionHasStartedException;
import fontys.s3.carspacebackend.exception.BadTokenException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccessTokenHelperTest {
    @Autowired
    private AccessTokenHelper tokenHelper;

    @Test
    void testEncodeAndDecodeToken() {
        IRole adminRole = AdminRole.builder().role("admin").build();
        User u = User.builder().id(5L).username("tosho").role(adminRole).build();

        String encodedToken = tokenHelper.generateAccessToken(u);

        AccessToken token = tokenHelper.decode(encodedToken);

        assertEquals(5, token.getUserId());
        assertEquals("tosho", token.getUsername());
        assertEquals("admin", token.getRole());
    }

    @Test
    void testEncodeAndDecodeTokenWithNullFields() {
        User u = User.builder().username("tosho").build();

        String encodedToken = tokenHelper.generateAccessToken(u);

        AccessToken token = tokenHelper.decode(encodedToken);

        assertNull(token.getUserId());
        assertEquals("tosho", token.getUsername());
        assertNull(token.getRole());
    }
    @Test
    void testDecodeInvalidTokenShouldThrowException() {
        String encodedToken = "maikati";

        Exception exception = assertThrows(BadTokenException.class, () -> {
            AccessToken token = tokenHelper.decode(encodedToken);
        });

        String expectedMessage = "401 UNAUTHORIZED \"JWT strings must contain exactly 2 period characters. Found: 0\"";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
