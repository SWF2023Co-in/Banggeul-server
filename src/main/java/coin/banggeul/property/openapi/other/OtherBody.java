package coin.banggeul.property.openapi.other;

import coin.banggeul.property.openapi.apt.AptItems;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OtherBody {

    @JsonProperty("items")
    private OtherItems items;
    private Long numOfRows;
    private Long pageNo;
    private Long totalCount;
}
