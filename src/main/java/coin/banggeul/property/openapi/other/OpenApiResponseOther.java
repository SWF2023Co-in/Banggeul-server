package coin.banggeul.property.openapi.other;

import coin.banggeul.property.openapi.apt.AptResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OpenApiResponseOther {
    private OtherResponse response;
}
