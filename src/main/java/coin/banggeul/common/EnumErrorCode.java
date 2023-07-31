package coin.banggeul.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public enum EnumErrorCode {


    ENUM_INVALID_STRING("넣어야 하는 값의 형태를 확인해주세요");

    private String defaultMessage;
}
