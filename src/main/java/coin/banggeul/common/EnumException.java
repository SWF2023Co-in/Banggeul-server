package coin.banggeul.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EnumException extends RuntimeException{

    private EnumErrorCode errorCode;
    private String errorMessage;

    public EnumException(EnumErrorCode errorCode) {
        super(errorCode.getDefaultMessage());
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDefaultMessage();
    }

    public EnumException(String message, EnumErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
        this.errorMessage = message;
    }
}
