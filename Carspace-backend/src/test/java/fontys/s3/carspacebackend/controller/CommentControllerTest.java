package fontys.s3.carspacebackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fontys.s3.carspacebackend.business.service.impl.CommentService;
import fontys.s3.carspacebackend.controller.requests.CreateCommentReq;
import fontys.s3.carspacebackend.controller.responses.ResourceCreatedResponse;
import fontys.s3.carspacebackend.controller.responses.ResourceDeletedResponse;
import fontys.s3.carspacebackend.domain.Comment;
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

import static fontys.s3.carspacebackend.ResponseBodyMatchers.responseBody;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class CommentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CommentService commentService;

    @Test
    @WithMockUser(username = "usernaem", roles = {"USER"})
    void createCommentShouldReturn201WhenRequestValid() throws Exception{
        ArgumentCaptor<Comment> commentCaptor = ArgumentCaptor.forClass(Comment.class);

        CreateCommentReq req = CreateCommentReq.builder().text("a comment").build();
        when(commentService.createComment(commentCaptor.capture(), eq(100L))).thenReturn(999L);

        ResourceCreatedResponse expectedRes = ResourceCreatedResponse.builder().id(999L).message("Comment created!").build();

        mockMvc.perform(post("/comment/100")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(req)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(responseBody().containsObjectAsJson(expectedRes, ResourceCreatedResponse.class));


        verify(commentService).createComment(commentCaptor.capture(), eq(100L));
    }

    @Test
    @WithMockUser(username = "usernaem", roles = {"USER"})
    void createCommentShouldReturn400WhenMissingFields() throws Exception{
        CreateCommentReq req = CreateCommentReq.builder().text("").build();

        mockMvc.perform(post("/comment/100")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(req)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(responseBody().containsError("text", "must not be blank"))
                .andExpect(responseBody().containsError("text", "length must be between 5 and 100"));

        verifyNoInteractions(commentService);
    }

    @Test
    @WithMockUser(username = "usernaem", roles = {"USER"})
    void deleteShouldReturn200WhenRequestValid() throws Exception{
        when(commentService.deleteComment(111L)).thenReturn(true);

        ResourceDeletedResponse expectedRes = ResourceDeletedResponse.builder().message("Comment deleted!").build();

        mockMvc.perform(delete("/comment/111")
                        .contentType(APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(responseBody().containsObjectAsJson(expectedRes, ResourceDeletedResponse.class));


        verify(commentService).deleteComment(111L);
    }
}
