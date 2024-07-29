package org.likelion.zagabi.Domain.Account.Jwt.Exception;

import lombok.Getter;
import org.likelion.zagabi.Global.Common.BaseErrorCode;
import org.likelion.zagabi.Global.Common.Exception.CustomException;

@Getter
public class SecurityCustomException extends CustomException {

    private final Throwable cause;

    public SecurityCustomException(BaseErrorCode errorCode) {
        super(errorCode);
        this.cause = null;
    }

    public SecurityCustomException(BaseErrorCode errorCode, Throwable cause) {
        super(errorCode);
        this.cause = cause;
    }
}
