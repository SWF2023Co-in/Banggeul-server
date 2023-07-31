package coin.banggeul.auth.exception;

import lombok.Getter;

@Getter
public class AuthException extends RuntimeException{
    private AuthErrorCode errorCode;
    private String errorMessage;

    public AuthException(AuthErrorCode code) {
        super(code.getDefaultMessage());
        this.errorCode = code;
        this.errorMessage = code.getDefaultMessage();
    }

    public AuthException(AuthErrorCode code, String message) {
        super(code.getDefaultMessage());
        this.errorCode = code;
        this.errorMessage = message;
    }
}
