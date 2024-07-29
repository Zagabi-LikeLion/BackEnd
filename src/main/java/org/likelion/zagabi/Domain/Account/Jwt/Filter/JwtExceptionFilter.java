package org.likelion.zagabi.Domain.Account.Jwt.Filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.likelion.zagabi.Domain.Account.Jwt.Exception.SecurityCustomException;
import org.likelion.zagabi.Domain.Account.Jwt.Exception.TokenErrorCode;
import org.likelion.zagabi.Domain.Account.Jwt.Util.HttpResponseUtil;
import org.likelion.zagabi.Global.Common.BaseErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws IOException {
        try {
            //요청을 다음 필터로 전달
            filterChain.doFilter(request, response);
        } catch (SecurityCustomException e) {
            log.warn(">>>>> SecurityCustomException : ", e);
            BaseErrorCode errorCode = e.getErrorCode();

            // 에러 응답 객체를 맵으로 생성
            //securityCustomException 예외가 발생했을 때
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", errorCode.getCode());
            errorResponse.put("message", errorCode.getMessage());
            errorResponse.put("details", e.getMessage());

            // HttpResponseUtil 클래스를 사용하여 응답 객체에 에러 정보를 설정
            HttpResponseUtil.setErrorResponse(
                    response,
                    errorCode.getHttpStatus(),
                    errorResponse
            );
        } catch (Exception e) {
            log.error(">>>>> Exception : ", e);

            // 에러 응답 객체를 맵으로 생성
            //일반적인 Exception 예외가 발생했을 때
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", TokenErrorCode.INTERNAL_SECURITY_ERROR.getCode());
            errorResponse.put("message", TokenErrorCode.INTERNAL_SECURITY_ERROR.getMessage());
            errorResponse.put("details", e.getMessage());

            // HttpResponseUtil 클래스를 사용하여 응답 객체에 에러 정보를 설정
            HttpResponseUtil.setErrorResponse(
                    response,
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    errorResponse
            );
        }
    }
}