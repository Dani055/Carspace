package fontys.s3.Carspacebackend.controller.requests;


import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.sql.Timestamp;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAuctionReq {
    private List<String> urls;

    @NotNull
    @NotBlank
    @Length(min = 2, max = 15)
    private String carBrand;

    @NotNull
    @NotBlank
    @Length(min = 2, max = 20)
    private String carModel;

    @NotNull
    @NotBlank
    @Length(min = 10, max = 255)
    private String carDesc;

    @NotNull
    @Min(1920)
    @Max(2030)
    private int carYear;

    @NotNull
    @Min(0)
    private double startingPrice;

    @NotNull
    @Min(0)
    private double buyoutPrice;

    @NotNull
    @Min(0)
    private int mileage;

    @NotNull
    @NotBlank
    @Length(min = 4, max = 255)
    private String location;

    @NotNull
    private Timestamp startsOn;

    @NotNull
    private Timestamp endsOn;

}
