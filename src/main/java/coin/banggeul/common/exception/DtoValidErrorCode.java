package coin.banggeul.common.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public enum DtoValidErrorCode {
    BAD_INPUT("값이 유효하지 않습니다.");

    private String defaultMessage;
}
