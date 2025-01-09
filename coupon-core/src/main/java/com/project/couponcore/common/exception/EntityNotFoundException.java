package com.project.couponcore.common.exception;

import com.project.couponcore.common.response.ErrorCode;

public class EntityNotFoundException extends BaseException {

    public EntityNotFoundException(String errorMsg) {
        super(errorMsg, ErrorCode.NOT_FOUND_ENTITY);
    }
}
