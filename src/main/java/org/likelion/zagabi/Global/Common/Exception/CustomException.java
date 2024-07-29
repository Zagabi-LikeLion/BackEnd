package org.likelion.zagabi.Global.Common.Exception;

import lombok.Getter;
import org.likelion.zagabi.Global.Common.BaseErrorCode;

@Getter
public class CustomException extends RuntimeException {

    private final BaseErrorCode errorCode;

    public CustomException(BaseErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
