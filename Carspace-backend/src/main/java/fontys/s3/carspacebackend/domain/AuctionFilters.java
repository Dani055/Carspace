package fontys.s3.carspacebackend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuctionFilters {
    String carBrand;
    String carModel;
    String location;
    int minYear;
    int maxYear;
    double minPrice;
    double maxPrice;
    int minMileage;
    int maxMileage;
    boolean hasEnded;
}
