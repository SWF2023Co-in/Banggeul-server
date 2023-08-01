package coin.banggeul.property.openapi.apt;

import coin.banggeul.property.openapi.apt.AptResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OpenApiResponseApt {
    private AptResponse response;
}
