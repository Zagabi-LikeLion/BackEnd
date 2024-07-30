package org.likelion.zagabi.Domain.Account.Jwt.Exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.likelion.zagabi.Domain.Account.Jwt.Util.HttpResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException {
        HttpStatus httpStatus;

        log.error(">>>>>> AuthenticationException: ", authException);
        httpStatus = HttpStatus.UNAUTHORIZED;
        // 에러 응답 객체를 맵으로 생성
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("code", "UNAUTHORIZED");
        errorResponse.put("message", "Unauthorized access");
        errorResponse.put("details", authException.getMessage());

        // HttpResponseUtil 클래스를 사용하여 응답 객체에 에러 정보를 설정
        HttpResponseUtil.setErrorResponse(response, httpStatus, errorResponse);
    }
}
