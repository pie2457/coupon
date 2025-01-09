package com.project.couponcore.domain.couponissue;

public interface CouponIssueReader {
    void checkAlreadyIssuance(long couponId, long userId);
}
