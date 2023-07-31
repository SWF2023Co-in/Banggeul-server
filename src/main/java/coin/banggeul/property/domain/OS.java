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
public enum OS {

    @JsonProperty("OPEN")
    OPEN("OPEN", "오픈형"),

    @JsonProperty("SEPERATED")
    SEPERATED("SEPERATED", "분리형");

    @JsonValue
    private String code;
    private String name;

    public static OS of(String name) {
        return Arrays.stream(OS.values())
                .filter(r -> r.getCode().equals(name.toUpperCase()))
                .findAny()
                .orElseThrow(() -> new EnumException(ENUM_INVALID_STRING));
    }
}
