package coin.banggeul.property.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FilterValue implements Serializable {

    private String rentalType;
    private String homeType;
    private Long areaMin;
    private Long areaMax;
    private Double depositMin;
    private Double depositMax;
    private Double monthlyMin;
    private Double monthlyMax;
    private List<String> options;

    @Builder
    public FilterValue(String rentalType,
                       String homeType,
                       Long areaMin,
                       Long areaMax,
                       Double depositMin,
                       Double depositMax,
                       Double monthlyMin,
                       Double monthlyMax,
                       List<String> options) {
        this.rentalType = rentalType;
        this.homeType = homeType;
        this.areaMin = areaMin;
        this.areaMax = areaMax;
        this.depositMin = depositMin;
        this.depositMax = depositMax;
        this.monthlyMin = monthlyMin;
        this.monthlyMax = monthlyMax;
        this.options = options;
    }
}
