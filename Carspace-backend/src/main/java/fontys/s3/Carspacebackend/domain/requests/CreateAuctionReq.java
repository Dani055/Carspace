package fontys.s3.Carspacebackend.domain.requests;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAuctionReq {
    private List<String> urls;
    private Long userId;

    private String carBrand;

    private String carModel;

    private String carDesc;

    private Date carYear;

    private double startingPrice;

    private double buyoutPrice;

    private int mileage;

    private String location;

    private Timestamp startsOn;

    private Timestamp endsOn;

}
