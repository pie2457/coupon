package com.project.couponcore.infrastructure.couponissue.redis;

import com.project.couponcore.common.exception.BadRequestException;
import com.project.couponcore.common.exception.InvalidParamException;
import com.project.couponcore.common.response.ErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CouponIssueRequestCode {
    SUCCESS(1),
    DUPLICATE_COUPON_ISSUE(2),
    INVALID_COUPON_ISSUE(3);

    private final int code;

    public static CouponIssueRequestCode find(String code) {
        int codeValue = Integer.valueOf(code);

        if (codeValue == 1)
            return SUCCESS;
        if (codeValue == 2)
            return DUPLICATE_COUPON_ISSUE;
        if (codeValue == 3)
            return INVALID_COUPON_ISSUE;
        throw new InvalidParamException("존재하지 않는 코드입니다. %s".formatted(code));
    }

    public static void checkRequestResult(CouponIssueRequestCode code) {
        if (code == INVALID_COUPON_ISSUE) {
            throw new InvalidParamException("발급 가능한 수량을 초과했습니다.");
        }
        if (code == DUPLICATE_COUPON_ISSUE) {
            throw new BadRequestException(ErrorCode.DUPLICATED_ENTITY, "이미 발급된 쿠폰입니다.");
        }
    }
}
