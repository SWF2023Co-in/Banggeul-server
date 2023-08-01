package coin.banggeul.member;

import coin.banggeul.auth.domain.LoginService;
import coin.banggeul.auth.service.JwtService;
import coin.banggeul.common.AppProperties;
import coin.banggeul.common.response.BasicResponse;
import coin.banggeul.common.response.ResponseUtil;
import coin.banggeul.member.dto.AuthInfo;
import coin.banggeul.member.dto.NameResponse;
import coin.banggeul.member.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final LoginService loginService;
    private final JwtService jwtService;

    @ResponseBody
    @GetMapping("/login/kakao")
    public BasicResponse<AuthInfo> loginKaKao(@RequestParam String code) {
        return ResponseUtil.success(loginService.login(code));
    }

    @ResponseBody
    @PostMapping("/reissue")
    public BasicResponse<AuthInfo> reissue(@RequestBody TokenDto dto) {
        return ResponseUtil.success(jwtService.reissue(dto));
    }

    @ResponseBody
    @GetMapping("/me")
    public BasicResponse<NameResponse> printName() {
        return ResponseUtil.success(new NameResponse(memberService.findCurrentMember().getName()));
    }

}
