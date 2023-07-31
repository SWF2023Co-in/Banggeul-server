package coin.banggeul.auth.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public enum ProviderErrorCode {

    KAKAO("카카오에서 정보를 가져오는데 실패하였습니다.");

    private String defaultMessage;
}

