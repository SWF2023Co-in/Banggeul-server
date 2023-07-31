package coin.banggeul.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProviderException extends RuntimeException{

    private ProviderErrorCode errorCode;
    private String errorMessage;

    public ProviderException(ProviderErrorCode errorCode) {
        super(errorCode.getDefaultMessage());
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDefaultMessage();
    }

    public ProviderException(String message, ProviderErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
        this.errorMessage = message;
    }
}
