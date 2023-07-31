package coin.banggeul.auth.domain;

import coin.banggeul.common.response.ResponseUtil;
import coin.banggeul.member.dto.AuthInfo;

public interface LoginService {

    AuthInfo login(String code);
}
