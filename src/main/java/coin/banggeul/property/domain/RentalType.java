package coin.banggeul.property.domain;

import coin.banggeul.common.EnumException;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;

import static coin.banggeul.common.EnumErrorCode.ENUM_INVALID_STRING;


@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public enum RentalType {

    @JsonProperty("LUMPSUM")
    LUMPSUM("LUMPSUM", "전세"),

    @JsonProperty("MONTHLY")
    MONTHLY("MONTHLY", "월세");

    @JsonValue
    private String code;
    private String name;

    public static RentalType of(String name) {
        return Arrays.stream(RentalType.values())
                .filter(r -> r.getCode().equals(name.toUpperCase()))
                .findAny()
                .orElseThrow(() -> new EnumException(ENUM_INVALID_STRING));
    }
}
