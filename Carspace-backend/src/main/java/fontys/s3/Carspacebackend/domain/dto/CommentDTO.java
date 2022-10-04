package fontys.s3.Carspacebackend.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class CommentDTO {
    private Long id;

    private String text;

    private Date createdOn;

    private UserDTO creator;
}
