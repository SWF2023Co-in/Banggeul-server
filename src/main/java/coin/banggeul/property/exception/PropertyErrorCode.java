package coin.banggeul.property.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public enum PropertyErrorCode {

    THUMBNAIL_NOT_FOUND("썸네일을 찾을 수 없습니다."),
    PROPERTY_NOT_FOUND("해당 매물을 찾을 수 없습니다.");

    private String defaultMessage;
}
