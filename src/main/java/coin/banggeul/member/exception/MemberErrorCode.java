package coin.banggeul.member.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public enum MemberErrorCode {

    MEMBER_NOT_FOUND("해당 회원을 찾을 수 없습니다.");

    private String defaultMessage;
}
