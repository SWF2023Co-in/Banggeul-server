package coin.banggeul.property.openapi.other;

import coin.banggeul.property.openapi.Header;
import coin.banggeul.property.openapi.apt.AptBody;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OtherResponse {

    @JsonProperty("header")
    private Header header;

    @JsonProperty("body")
    private OtherBody body;
}
