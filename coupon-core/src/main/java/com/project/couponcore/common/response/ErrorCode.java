package com.project.couponcore.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    INVALID_PARAMETER("요청한 값이 올바르지 않습니다."),
    NOT_FOUND_ENTITY("존재하지 않는 엔티티입니다."),
    DUPLICATED_ENTITY("이미 존재하는 엔티티입니다.");

    private final String errorMsg;

    public String getErrorMsg(Object... arg) {
        return String.format(errorMsg, arg);
    }
}
