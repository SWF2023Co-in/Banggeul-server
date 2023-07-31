package coin.banggeul.auth.exception;

import coin.banggeul.common.response.BasicResponse;
import coin.banggeul.common.response.ErrorEntity;
import coin.banggeul.common.response.ResponseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class AuthExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);
        } catch (AuthException e) {
            sendError(response, e.getErrorCode(), e.getMessage());
        }

    }

    public void sendError(HttpServletResponse response, AuthErrorCode code, String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        BasicResponse<Object> basicResponse = ResponseUtil.error(
                new ErrorEntity(code.toString(), message, null)
        );
        try{
            response.setCharacterEncoding("utf-8");
            response.setStatus(401);
            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(basicResponse));
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
