package coin.banggeul.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthInfo {

    private String accessToken;
    private String refreshToken;
}
