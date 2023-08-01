package coin.banggeul.property.openapi.officetel;

import coin.banggeul.property.openapi.Header;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OfficetelResponse {

    @JsonProperty("header")
    private Header header;

    @JsonProperty("body")
    private OfficetelBody body;
}
