package baeksaitong.sofp.global.jwt.error;

import baeksaitong.sofp.global.error.code.ErrorCode;
import baeksaitong.sofp.global.error.code.CommonErrorCode;
import baeksaitong.sofp.global.error.dto.ErrorResponse;
import baeksaitong.sofp.global.jwt.error.code.JwtErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        String exception = (String)request.getAttribute("exception");

        if (exception == null) {
            setErrorResponse(response, CommonErrorCode.UNAUTHORIZED);
        }
        else if (exception.equals(JwtErrorCode.INVALID_JWT_TOKEN.getCode())) {
            setErrorResponse(response, JwtErrorCode.INVALID_JWT_TOKEN);
        }
        else if (exception.equals(JwtErrorCode.EXPIRED_JWT_TOKEN.getCode())) {
            setErrorResponse(response, JwtErrorCode.EXPIRED_JWT_TOKEN);
        }
        else {
            setErrorResponse(response, CommonErrorCode.UNAUTHORIZED);
        }
    }

    private void setErrorResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ErrorResponse errorResponse = ErrorResponse.of(errorCode);

        ObjectMapper objectMapper = new ObjectMapper();
        String errorResponseJson = objectMapper.writeValueAsString(errorResponse);

        response.getWriter().print(errorResponseJson);
    }
}
