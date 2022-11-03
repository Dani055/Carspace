package fontys.s3.Carspacebackend.service;

import fontys.s3.Carspacebackend.business.service.impl.UserService;
import fontys.s3.Carspacebackend.domain.User;
import fontys.s3.Carspacebackend.domain.impl.UserRole;
import fontys.s3.Carspacebackend.exception.IncorrectCredentialsException;
import fontys.s3.Carspacebackend.persistence.repository.impl.RoleRepository;
import fontys.s3.Carspacebackend.persistence.repository.impl.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private RoleRepository roleRepoMock;

    @Mock
    private UserRepository userRepoMock;

    @InjectMocks
    private UserService userService;

    @Test
    void registerUser() {
        UserRole role = UserRole.builder().id(1L).role("user").build();
        User toRegister = User.builder().id(50L).username("jdoe").password("pass").firstName("john").lastName("doe").email("jdoe@gmail.com").address("avenue 123").phone("+311").build();

        when(roleRepoMock.findById(1L)).thenReturn(role);
        when(userRepoMock.saveUser(toRegister)).thenReturn(toRegister.getId());

        assertEquals(50L, userService.registerUser(toRegister));
        verify(roleRepoMock).findById(1L);
        verify(userRepoMock).saveUser(toRegister);
    }

    @Test
    void loginUserWithIncorrectPassword() {
        String username = "jdoe";
        String password = "pass";
        User toLogin = User.builder().id(50L).username("jdoe").password("pass").firstName("john").lastName("doe").email("jdoe@gmail.com").address("avenue 123").phone("+311").build();

        when(userRepoMock.getUserByUsername(username)).thenReturn(toLogin);

        Exception exception = assertThrows(IncorrectCredentialsException.class, () -> {
            userService.loginUser(username, password);
        });

        String expectedMessage = "Incorrect username or password";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(userRepoMock).getUserByUsername(username);
    }

    @Test
    void loginUserCorrectPassword() {
        String username = "jdoe";
        String password = "pass";
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        User toLogin = User.builder().id(50L).username("jdoe").password(encodedPassword).firstName("john").lastName("doe").email("jdoe@gmail.com").address("avenue 123").phone("+311").build();

        when(userRepoMock.getUserByUsername(username)).thenReturn(toLogin);

        User found = userService.loginUser(username, password);
        assertEquals(toLogin, found);
        verify(userRepoMock).getUserByUsername(username);
    }

    @Test
    void getUserById() {
        User toFind = User.builder().id(50L).username("jdoe").password("pass").firstName("john").lastName("doe").email("jdoe@gmail.com").address("avenue 123").phone("+311").build();
        when(userRepoMock.findById(50L)).thenReturn(toFind);

        User found = userService.getUserById(toFind.getId());

        assertEquals(toFind, found);
        verify(userRepoMock).findById(50L);
    }
}