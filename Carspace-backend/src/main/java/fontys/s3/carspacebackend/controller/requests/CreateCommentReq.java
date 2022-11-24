package fontys.s3.carspacebackend.controller.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommentReq {
    @NotBlank
    @Length(min = 5, max = 100)
    private String text;
}
