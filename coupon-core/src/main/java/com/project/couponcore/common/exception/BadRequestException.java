package com.project.couponcore.common.exception;

import com.project.couponcore.common.response.ErrorCode;

public class BadRequestException extends BaseException {

    public BadRequestException(ErrorCode errorCode, String errorMsg) {
        super(errorMsg, errorCode);
    }
}
