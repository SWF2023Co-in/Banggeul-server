package coin.banggeul.common.exception;

import coin.banggeul.auth.exception.AuthException;
import coin.banggeul.auth.exception.ProviderException;
import coin.banggeul.auth.exception.S3Exception;
import coin.banggeul.common.response.BasicResponse;
import coin.banggeul.common.response.ErrorEntity;
import coin.banggeul.common.response.ResponseUtil;
import coin.banggeul.member.exception.MemberException;
import coin.banggeul.property.exception.PropertyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CommonExceptionAdvice extends RuntimeException{

    @ExceptionHandler(AuthException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BasicResponse<ErrorEntity> authException(AuthException ex) {
        log.error("Authorization Exception[{}]: {}", ex.getErrorCode().toString(), ex.getErrorMessage());
        return ResponseUtil.error(new ErrorEntity(ex.getErrorCode().toString(), ex.getErrorMessage()));
    }

    @ExceptionHandler(ProviderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BasicResponse<ErrorEntity> providerException(ProviderException ex) {
        log.error("Provider Exception[{}]: {}", ex.getErrorCode().toString(), ex.getErrorMessage());
        return ResponseUtil.error(new ErrorEntity(ex.getErrorCode().toString(), ex.getErrorMessage()));
    }

    @ExceptionHandler(EnumException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BasicResponse<ErrorEntity> enumException(EnumException ex) {
        log.error("Enum Exception[{}]: {}", ex.getErrorCode().toString(), ex.getErrorMessage());
        return ResponseUtil.error(new ErrorEntity(ex.getErrorCode().toString(), ex.getErrorMessage()));
    }

    @ExceptionHandler(MemberException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public BasicResponse<ErrorEntity> memberException(MemberException ex) {
        log.error("Member Exception[{}]: {}", ex.getErrorCode().toString(), ex.getErrorMessage());
        return ResponseUtil.error(new ErrorEntity(ex.getErrorCode().toString(), ex.getErrorMessage()));
    }

    @ExceptionHandler(PropertyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BasicResponse<ErrorEntity> propertyException(PropertyException ex) {
        log.error("Property Exception[{}]: {}", ex.getErrorCode().toString(), ex.getErrorMessage());
        return ResponseUtil.error(new ErrorEntity(ex.getErrorCode().toString(), ex.getErrorMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BasicResponse<ErrorEntity> dtoValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors()
                .forEach(c -> errors.put(((FieldError) c).getField(), c.getDefaultMessage()));
        log.error("Dto Validation Exception({}): {}", DtoValidErrorCode.BAD_INPUT, errors);
        return ResponseUtil.error(new ErrorEntity(DtoValidErrorCode.BAD_INPUT.toString(),
                DtoValidErrorCode.BAD_INPUT.getDefaultMessage(),
                errors));
    }

    @ExceptionHandler(S3Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BasicResponse<ErrorEntity> s3Exception(S3Exception ex) {
        log.error("S3 Image Exception[{}]: {}", ex.getErrorCode().toString(), ex.getErrorMessage());
        return ResponseUtil.error(new ErrorEntity(ex.getErrorCode().toString(), ex.getErrorMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BasicResponse<ErrorEntity> otherExceptions(RuntimeException ex) {
        log.error("Other Exception[{}]: {}", "기타 에러", ex.getMessage());
        return ResponseUtil.error(new ErrorEntity("기타 에러", ex.getMessage()));
    }
}
