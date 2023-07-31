package coin.banggeul.property.exception;

import coin.banggeul.member.exception.MemberErrorCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PropertyException extends RuntimeException{

    private PropertyErrorCode errorCode;
    private String errorMessage;

    public PropertyException(PropertyErrorCode errorCode) {
        super(errorCode.getDefaultMessage());
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDefaultMessage();
    }

    public PropertyException(String message, PropertyErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
        this.errorMessage = message;
    }
}
