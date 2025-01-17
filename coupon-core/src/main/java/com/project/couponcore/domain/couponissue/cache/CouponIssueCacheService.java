package com.project.couponcore.domain.couponissue.cache;

public interface CouponIssueCacheService {
    void checkCouponIssueQuantity(CouponIssueCache couponIssueCache, long userId);
}
