package fontys.s3.Carspacebackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fontys.s3.Carspacebackend.business.service.impl.AuctionService;
import fontys.s3.Carspacebackend.controller.requests.CreateAuctionReq;
import fontys.s3.Carspacebackend.controller.responses.ResourceChangedResponse;
import fontys.s3.Carspacebackend.controller.responses.ResourceCreatedResponse;
import fontys.s3.Carspacebackend.controller.responses.ResourceDeletedResponse;
import fontys.s3.Carspacebackend.domain.*;
import fontys.s3.Carspacebackend.domain.impl.UserRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static fontys.s3.Carspacebackend.ResponseBodyMatchers.responseBody;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuctionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AuctionService auctionServiceMock;

    @Test
    @WithMockUser(username = "usernaem", roles = {"user"})
    void createAuctionShouldReturn201WhenRequestValid() throws Exception{
        ArgumentCaptor<Auction> auctionCaptor = ArgumentCaptor.forClass(Auction.class);
        List<String> urls = new ArrayList<>();
        urls.add("xd1");
        CreateAuctionReq req = CreateAuctionReq.builder().urls(urls).carBrand("BMW").carModel("330i").carDesc("descdescde").carYear(2002).startingPrice(1000).buyoutPrice(2000).mileage(120000).startsOn(Instant.now()).endsOn(Instant.now()).location("123 avenue").build();
        when(auctionServiceMock.createAuction(auctionCaptor.capture(), eq(urls))).thenReturn(100L);

        ResourceCreatedResponse expectedRes = ResourceCreatedResponse.builder().id(100L).message("Auction created!").build();

        mockMvc.perform(post("/auction")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(req)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(responseBody().containsObjectAsJson(expectedRes, ResourceCreatedResponse.class));


        verify(auctionServiceMock).createAuction(auctionCaptor.capture(), eq(urls));
    }

    @Test
    @WithMockUser(username = "usernaem", roles = {"user"})
    void createAuctionShouldReturn400WhenMissingFields() throws Exception{
        CreateAuctionReq req = CreateAuctionReq.builder().carBrand("").carModel("").carDesc("").carYear(0).startingPrice(-1).buyoutPrice(-1).mileage(-1).startsOn(null).endsOn(null).location("").build();

        mockMvc.perform(post("/auction")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(req)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(responseBody().containsError("carBrand", "must not be blank"))
                .andExpect(responseBody().containsError("carBrand", "length must be between 2 and 15"))
                .andExpect(responseBody().containsError("carModel", "must not be blank"))
                .andExpect(responseBody().containsError("carModel", "length must be between 2 and 20"))
                .andExpect(responseBody().containsError("carDesc", "must not be blank"))
                .andExpect(responseBody().containsError("carDesc", "length must be between 10 and 255"))
                .andExpect(responseBody().containsError("startingPrice", "must be greater than or equal to 0"));

        verifyNoInteractions(auctionServiceMock);
    }

    @Test
    void getAuctionByIdShouldReturn200AndAuction() throws Exception{
        Instant aucStart = Instant.parse("2022-07-09T15:30:00.00Z");
        Instant aucEnd = Instant.parse("2022-07-15T15:30:00.00Z");
        Instant time = Instant.parse("2022-07-13T12:30:00.00Z");
        UserRole role = UserRole.builder().id(1L).role("user").build();
        User aucCreator = User.builder().id(50L).username("jdoe").role(role).password("pass").firstName("john").lastName("doe").email("jdoe@gmail.com").address("avenue 123").phone("+311").build();
        HashSet<Bid> bids = new HashSet<>();
        bids.add(Bid.builder().id(24L).amount(1500.0).createdOn(time).build());

        List<Image> images = new ArrayList<>();
        images.add(Image.builder().imgUrl("xd1").id(11L).build());
        Auction auction = Auction.builder().id(100L).creator(aucCreator).images(images).carBrand("BMW").carModel("330i").carDesc("desc").carYear(2002).startingPrice(1000).buyoutPrice(2000).hasSold(false).startsOn(aucStart).endsOn(aucEnd).bids(bids).build();

        when(auctionServiceMock.getAuctionDetails(100L)).thenReturn(auction);


        mockMvc.perform(get("/auction/100")
                        .contentType(APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                                                       {"id":100,
                                                       "carBrand":"BMW",
                                                       "carModel":"330i",
                                                       "carDesc":"desc",
                                                       "carYear":2002,
                                                       "startingPrice":1000.0,
                                                       "buyoutPrice":2000.0,
                                                       "mileage":0,
                                                       "hasSold":false,
                                                       "location":null,
                                                       "startsOn":"2022-07-09T15:30:00Z",
                                                       "endsOn":"2022-07-15T15:30:00Z",
                                                       "creator":{"id":50,"role":"user","username":"jdoe","firstName":"john","lastName":"doe","email":"jdoe@gmail.com","address":"avenue 123","phone":"+311"},
                                                       "images":[{"id":11,"imgUrl":"xd1"}],
                                                       "comments":[],
                                                       "bids":[{"id":24,"amount":1500.0,"createdOn":"2022-07-13T12:30:00Z","bidder":null}],
                                                       "winningBid":null}
                                                    """));


        verify(auctionServiceMock).getAuctionDetails(100L);
    }

    @Test
    @WithMockUser(username = "usernaem", roles = {"user"})
    void editAuctionShouldReturn200WhenRequestValid() throws Exception{
        ArgumentCaptor<Auction> auctionCaptor = ArgumentCaptor.forClass(Auction.class);
        List<String> urls = new ArrayList<>();
        urls.add("xd1");
        CreateAuctionReq req = CreateAuctionReq.builder().urls(urls).carBrand("BMW").carModel("330i").carDesc("descdescde").carYear(2002).startingPrice(1000).buyoutPrice(2000).mileage(120000).startsOn(Instant.now()).endsOn(Instant.now()).location("123 avenue").build();
        when(auctionServiceMock.editAuction(auctionCaptor.capture(), eq(urls))).thenReturn(100L);

        ResourceChangedResponse expectedRes = ResourceChangedResponse.builder().id(100L).message("Auction edited!").build();

        mockMvc.perform(put("/auction/100")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(req)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(responseBody().containsObjectAsJson(expectedRes, ResourceChangedResponse.class));


        verify(auctionServiceMock).editAuction(auctionCaptor.capture(), eq(urls));
    }

    @Test
    @WithMockUser(username = "usernaem", roles = {"user"})
    void editAuctionShouldReturn400WhenMissingFields() throws Exception{
        CreateAuctionReq req = CreateAuctionReq.builder().carBrand("").carModel("").carDesc("").carYear(0).startingPrice(-1).buyoutPrice(-1).mileage(-1).startsOn(null).endsOn(null).location("").build();

        mockMvc.perform(put("/auction/100")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(req)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE));
        //NOT TESTING MESSAGES BECAUSE IT SHARES THE SAME REQUEST AS CREATE AUCTION

        verifyNoInteractions(auctionServiceMock);
    }
    @Test
    @WithMockUser(username = "usernaem", roles = {"user"})
    void deleteAuctionShouldReturn200WhenRequestValid() throws Exception{
        when(auctionServiceMock.deleteAuction(100L)).thenReturn(true);

        ResourceDeletedResponse expectedRes = ResourceDeletedResponse.builder().message("Auction deleted").build();

        mockMvc.perform(delete("/auction/100")
                        .contentType(APPLICATION_JSON_VALUE)
                        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(responseBody().containsObjectAsJson(expectedRes, ResourceDeletedResponse.class));


        verify(auctionServiceMock).deleteAuction(100L);
    }
}
