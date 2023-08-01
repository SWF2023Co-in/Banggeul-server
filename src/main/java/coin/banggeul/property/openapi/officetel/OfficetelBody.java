package coin.banggeul.property.openapi.officetel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OfficetelBody {

    @JsonProperty("items")
    private OfficetelItems items;
    private Long numOfRows;
    private Long pageNo;
    private Long totalCount;
}
