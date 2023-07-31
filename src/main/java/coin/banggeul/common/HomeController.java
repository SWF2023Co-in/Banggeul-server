package coin.banggeul.common;

import coin.banggeul.auth.service.JwtService;
import coin.banggeul.common.response.BasicResponse;
import coin.banggeul.common.response.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class HomeController {

    private final JwtService jwtService;

    @GetMapping("/")
    public BasicResponse<String> home() {
        return ResponseUtil.success("This is home for Banggeul.");
    }

    @GetMapping("/health")
    public String healthCheck() {
        return "Health Check";
    }

    @GetMapping("/check")
    public String tokenCheck(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7); // "Bearer "
        return jwtService.checkToken(token);
    }
}
