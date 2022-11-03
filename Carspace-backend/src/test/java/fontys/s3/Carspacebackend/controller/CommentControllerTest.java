package fontys.s3.Carspacebackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fontys.s3.Carspacebackend.business.service.impl.BidService;
import fontys.s3.Carspacebackend.business.service.impl.CommentService;
import fontys.s3.Carspacebackend.controller.requests.CreateBidReq;
import fontys.s3.Carspacebackend.controller.requests.CreateCommentReq;
import fontys.s3.Carspacebackend.controller.responses.ResourceCreatedResponse;
import fontys.s3.Carspacebackend.domain.Bid;
import fontys.s3.Carspacebackend.domain.Comment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static fontys.s3.Carspacebackend.ResponseBodyMatchers.responseBody;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CommentController.class)
public class CommentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CommentService commentService;

    @Test
    void createCommentShouldReturn201WhenRequestValid() throws Exception{
        ArgumentCaptor<Comment> commentCaptor = ArgumentCaptor.forClass(Comment.class);

        CreateCommentReq req = CreateCommentReq.builder().text("a comment").build();
        when(commentService.createComment(commentCaptor.capture(), eq(100L), eq(25L))).thenReturn(999L);

        ResourceCreatedResponse expectedRes = ResourceCreatedResponse.builder().id(999L).message("Comment created!").build();

        mockMvc.perform(post("/comment/100")
                        .contentType(APPLICATION_JSON_VALUE)
                        .header("authorization", "Bearer " + 25)
                        .content(objectMapper.writeValueAsString(req)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(responseBody().containsObjectAsJson(expectedRes, ResourceCreatedResponse.class));


        verify(commentService).createComment(commentCaptor.capture(), eq(100L), eq(25L));
    }

    @Test
    void createCommentShouldReturn400WhenMissingFields() throws Exception{
        CreateCommentReq req = CreateCommentReq.builder().text("").build();

        mockMvc.perform(post("/comment/100")
                        .contentType(APPLICATION_JSON_VALUE)
                        .header("authorization", "Bearer " + 25)
                        .content(objectMapper.writeValueAsString(req)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(responseBody().containsError("text", "must not be blank"))
                .andExpect(responseBody().containsError("text", "length must be between 5 and 100"));

        verifyNoInteractions(commentService);
    }
}
