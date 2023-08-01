package coin.banggeul.property.domain;

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
public enum NSEW {

    @JsonProperty("NORTH")
    NORTH("NORTH"),

    @JsonProperty("SOUTH")
    SOUTH("SOUTH"),

    @JsonProperty("EAST")
    EAST("EAST"),

    @JsonProperty("WEST")
    WEST("WEST");

    @JsonValue
    private String name;

    public static NSEW of(String name) {
        return Arrays.stream(NSEW.values())
                .filter(r -> r.getName().equals(name.toUpperCase()))
                .findAny()
                .orElseThrow(() -> new EnumException(ENUM_INVALID_STRING));
    }
}
