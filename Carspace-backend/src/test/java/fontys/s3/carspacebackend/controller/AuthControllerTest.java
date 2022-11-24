package fontys.s3.carspacebackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fontys.s3.carspacebackend.business.service.impl.UserService;
import fontys.s3.carspacebackend.controller.requests.LoginReq;
import fontys.s3.carspacebackend.controller.responses.ResourceCreatedResponse;
import fontys.s3.carspacebackend.domain.User;
import fontys.s3.carspacebackend.domain.UserWithToken;
import fontys.s3.carspacebackend.domain.impl.UserRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static fontys.s3.carspacebackend.ResponseBodyMatchers.responseBody;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserService userServiceMock;

    @Test
    void createUserShouldReturn201WhenRequestValid() throws Exception{
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        User user = User.builder().username("test").password("123").firstName("John").lastName("Doe").email("jdoe@gmail.com").address("eindhoven").phone("+123124124").build();
        when(userServiceMock.registerUser(userCaptor.capture())).thenReturn(100L);

        ResourceCreatedResponse expectedRes = ResourceCreatedResponse.builder().id(100L).message("Register successful").build();

        mockMvc.perform(post("/auth/signup")
                .contentType(APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(user)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(responseBody().containsObjectAsJson(expectedRes, ResourceCreatedResponse.class));


        verify(userServiceMock).registerUser(userCaptor.capture());
    }

    @Test
    void createUserShouldReturn400_WhenMissingFields() throws Exception{
        User user = User.builder().username("").password("").firstName("").lastName("").email("").address("").phone("").build();
        MvcResult mvcResult = mockMvc.perform(post("/auth/signup")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(user)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(responseBody().containsError("username", "must not be blank"))
                .andExpect(responseBody().containsError("username", "length must be between 2 and 50"))
                .andExpect(responseBody().containsError("password","must not be blank"))
                .andExpect(responseBody().containsError("password","length must be between 3 and 50"))
                .andExpect(responseBody().containsError("firstName","must not be blank"))
                .andExpect(responseBody().containsError("firstName","length must be between 2 and 50"))
                .andExpect(responseBody().containsError("lastName","must not be blank"))
                .andExpect(responseBody().containsError("lastName","length must be between 2 and 50"))
                .andExpect(responseBody().containsError("email","must not be blank"))
                .andExpect(responseBody().containsError("email","length must be between 5 and 50"))
                .andExpect(responseBody().containsError("address","must not be blank"))
                .andExpect(responseBody().containsError("address","length must be between 2 and 50"))
                .andExpect(responseBody().containsError("phone","must not be blank"))
                .andExpect(responseBody().containsError("phone","length must be between 8 and 50"))
                .andReturn();

        verifyNoInteractions(userServiceMock);
    }

    @Test
    void loginUserShouldReturn200WhenRequestValid() throws Exception{
        LoginReq req = LoginReq.builder().username("test").password("123").build();
        UserRole role = UserRole.builder().id(1L).role("user").build();
        User user = User.builder().id(25L).username("test").password("123").role(role).firstName("John").lastName("Doe").email("jdoe@gmail.com").address("eindhoven").phone("+123124124").build();

        UserWithToken uwt = UserWithToken.builder().user(user).token("asdtoken").build();

        when(userServiceMock.loginUser(req.getUsername(), req.getPassword())).thenReturn(uwt);

        mockMvc.perform(post("/auth/signin")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(req)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                                                       {"message":"Login successful",
                                                       "obj": 
                                                       {
                                                        "user": {"id":25,
                                                            "role":"user",
                                                            "username":"test",
                                                            "firstName":"John",
                                                            "lastName":"Doe",
                                                            "email":"jdoe@gmail.com",
                                                            "address":"eindhoven",
                                                            "phone":"+123124124"
                                                            },
                                                        "accessToken": "asdtoken"
                                                       }
                                                            
                                                       }
                                                    """));


        verify(userServiceMock).loginUser(req.getUsername(), req.getPassword());
    }

    @Test
    void loginUserShouldReturn400_WhenMissingFields() throws Exception{
        LoginReq req = LoginReq.builder().username("").password("").build();
        mockMvc.perform(post("/auth/signin")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(req)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(responseBody().containsError("username", "must not be blank"))
                .andExpect(responseBody().containsError("password","must not be blank"))
                .andReturn();

        verifyNoInteractions(userServiceMock);
    }

    @Test
    @WithMockUser(username = "usernaem", roles = {"USER"})
    void checkKeyShouldReturn200WhenRequestValid() throws Exception{
        UserRole role = UserRole.builder().id(1L).role("user").build();
        User user = User.builder().id(25L).username("test").password("123").role(role).firstName("John").lastName("Doe").email("jdoe@gmail.com").address("eindhoven").phone("+123124124").build();

        when(userServiceMock.getUserByAccessToken()).thenReturn(user);

        mockMvc.perform(get("/auth/checkkey")
                        .contentType(APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                                                       {"message":"Auth key valid. Login successful",
                                                       "obj":
                                                            {"id":25,
                                                            "role":"user",
                                                            "username":"test",
                                                            "firstName":"John",
                                                            "lastName":"Doe",
                                                            "email":"jdoe@gmail.com",
                                                            "address":"eindhoven",
                                                            "phone":"+123124124"
                                                            }
                                                       }
                                                    """));


        verify(userServiceMock).getUserByAccessToken();
    }
}
