package coin.banggeul.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DtoValidException extends RuntimeException{

    private DtoValidErrorCode errorCode;
    private String errorMessage;

    public DtoValidException(DtoValidErrorCode errorCode) {
        super(errorCode.getDefaultMessage());
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDefaultMessage();
    }

    public DtoValidException(String message, DtoValidErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
        this.errorMessage = message;
    }
}
