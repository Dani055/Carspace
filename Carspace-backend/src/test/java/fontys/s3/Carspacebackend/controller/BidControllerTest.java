package fontys.s3.Carspacebackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fontys.s3.Carspacebackend.business.service.impl.AuctionService;
import fontys.s3.Carspacebackend.business.service.impl.BidService;
import fontys.s3.Carspacebackend.controller.requests.CreateAuctionReq;
import fontys.s3.Carspacebackend.controller.requests.CreateBidReq;
import fontys.s3.Carspacebackend.controller.responses.ResourceCreatedResponse;
import fontys.s3.Carspacebackend.domain.Auction;
import fontys.s3.Carspacebackend.domain.Bid;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static fontys.s3.Carspacebackend.ResponseBodyMatchers.responseBody;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BidController.class)
public class BidControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private BidService bidService;

    @Test
    void createBidShouldReturn201WhenRequestValid() throws Exception{
        ArgumentCaptor<Bid> bidCaptor = ArgumentCaptor.forClass(Bid.class);

        CreateBidReq req = CreateBidReq.builder().amount(1000.0).build();
        when(bidService.createBid(bidCaptor.capture(), eq(100L), eq(25L))).thenReturn(999L);

        ResourceCreatedResponse expectedRes = ResourceCreatedResponse.builder().id(999L).message("Bid placed!").build();

        mockMvc.perform(post("/bid/100")
                        .contentType(APPLICATION_JSON_VALUE)
                        .header("authorization", "Bearer " + 25)
                        .content(objectMapper.writeValueAsString(req)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(responseBody().containsObjectAsJson(expectedRes, ResourceCreatedResponse.class));


        verify(bidService).createBid(bidCaptor.capture(), eq(100L), eq(25L));
    }

    @Test
    void createBidShouldReturn400WhenMissingAmount() throws Exception{
        CreateBidReq req = CreateBidReq.builder().amount(-2.2).build();

        mockMvc.perform(post("/bid/100")
                        .contentType(APPLICATION_JSON_VALUE)
                        .header("authorization", "Bearer " + 25)
                        .content(objectMapper.writeValueAsString(req)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(responseBody().containsError("amount", "must be greater than or equal to 0"));


        verifyNoInteractions(bidService);
    }
}
