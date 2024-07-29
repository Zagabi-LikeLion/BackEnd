package org.likelion.zagabi.Domain.Account.Jwt.Util;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

/**
 * HTTP 응답을 설정할 때 사용되는 메서드
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpResponseUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void setSuccessResponse(HttpServletResponse response, HttpStatus httpStatus, Object body)
            throws IOException {
        //response = 현재 응답 객체
        // httpStatus = HTTP 상태
        //body = 응답 본문 객체
        log.info("[*] Success Response");
        String responseBody = objectMapper.writeValueAsString(ResponseEntity.ok(body));
        //응답 본문을 JSON 문자열로 반환
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        // 응답의 Content-Type을 JSON으로 설정
        response.setStatus(httpStatus.value());
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(responseBody);
    }

    public static void setErrorResponse(HttpServletResponse response, HttpStatus httpStatus, Object body)
            throws IOException {
        log.info("[*] Failure Response");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(httpStatus.value());
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getOutputStream(), body);
    }

}