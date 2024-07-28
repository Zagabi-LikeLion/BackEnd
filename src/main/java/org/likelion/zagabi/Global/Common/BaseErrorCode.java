package org.likelion.zagabi.Global.Common;

import org.springframework.http.HttpStatus;

import java.util.Map;

public interface BaseErrorCode {

    HttpStatus getHttpStatus();

    String getCode();

    String getMessage();

    Map<String, Object> getErrorResponse();

}