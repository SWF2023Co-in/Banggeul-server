package coin.banggeul.common;

import coin.banggeul.common.exception.EnumException;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;

import static coin.banggeul.common.exception.EnumErrorCode.ENUM_INVALID_STRING;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public enum YesNo {

    @JsonProperty("YES")
    YES("YES"),

    @JsonProperty("N")
    NO("NO");

    @JsonValue
    private String code;

    public static YesNo of(String name) {
        return Arrays.stream(YesNo.values())
                .filter(r -> r.getCode().equals(name))
                .findAny()
                .orElseThrow(() -> new EnumException(ENUM_INVALID_STRING));
    }
}
