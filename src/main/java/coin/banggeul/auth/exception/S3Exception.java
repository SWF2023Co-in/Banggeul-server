package coin.banggeul.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class S3Exception extends RuntimeException{

    private S3ErrorCode errorCode;
    private String errorMessage;

    public S3Exception(S3ErrorCode errorCode) {
        super(errorCode.getDefaultMessage());
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDefaultMessage();
    }

    public S3Exception(String message, S3ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
        this.errorMessage = message;
    }
}
