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
public enum RoomType {

    @JsonProperty("FLAT")
    APT("FLAT", "아파트"),

    @JsonProperty("VILLA")
    VILLA("VILLA", "빌라"),

    @JsonProperty("ONEROOM")
    ONEROOM("ONEROOM", "원룸"),

    @JsonProperty("OFFICETEL")
    OFFICE("OFFICETEL", "오피스텔");

    @JsonValue
    private String code;
    private String name;

    public static RoomType of(String name) {
        return Arrays.stream(RoomType.values())
                .filter(r -> r.getCode().equals(name.toUpperCase()))
                .findAny()
                .orElseThrow(() -> new EnumException(ENUM_INVALID_STRING));
    }
}
