package fontys.s3.carspacebackend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;



@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    private Long id;

    private String imgUrl;

}