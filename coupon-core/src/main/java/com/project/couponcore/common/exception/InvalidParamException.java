package com.project.couponcore.common.exception;

import com.project.couponcore.common.response.ErrorCode;

public class InvalidParamException extends BaseException {

    public InvalidParamException(String errorMsg) {
        super(errorMsg, ErrorCode.INVALID_PARAMETER);
    }
}
