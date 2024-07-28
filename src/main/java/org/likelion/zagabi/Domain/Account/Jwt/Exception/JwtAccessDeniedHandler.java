package org.likelion.zagabi.Domain.Account.Jwt.Exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.likelion.zagabi.Domain.Account.Jwt.Util.HttpResponseUtil;

import java.io.IOException;

/**
 * JWT 인증을 처리하는 과정에 사용자의 접근 권한이 거부되었을 때 실행된다.
 */
@Slf4j
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler{
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        log.warn("Access Denied: ", accessDeniedException);

        HttpResponseUtil.setErrorResponse(response, HttpStatus.FORBIDDEN,
                TokenErrorCode.FORBIDDEN.getErrorResponse());
    }
}
