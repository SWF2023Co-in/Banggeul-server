package coin.banggeul.property.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FilterValue {

    private String rentalType;
    private String homeType;
    private Long areaMin;
    private Long areaMax;
    private List<String> options;

    @Builder
    public FilterValue(String homeType, Long areaMin, Long areaMax, List<String> options) {
        this.homeType = homeType;
        this.areaMin = areaMin;
        this.areaMax = areaMax;
        this.options = options;
    }
}
