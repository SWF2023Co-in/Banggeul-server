package coin.banggeul.member.exception;

import coin.banggeul.auth.exception.ProviderErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberException extends RuntimeException{

    private MemberErrorCode errorCode;
    private String errorMessage;

    public MemberException(MemberErrorCode errorCode) {
        super(errorCode.getDefaultMessage());
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDefaultMessage();
    }

    public MemberException(String message, MemberErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
        this.errorMessage = message;
    }
}
