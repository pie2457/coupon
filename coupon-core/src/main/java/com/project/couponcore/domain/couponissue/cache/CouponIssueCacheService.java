package com.project.couponcore.domain.couponissue.cache;

import com.project.couponcore.domain.couponissue.CouponIssueCommand;

public interface CouponIssueCacheService {
    void checkCouponIssueQuantity(CouponIssueCache couponIssueCache, long userId);

    boolean existCouponIssueTarget();

    CouponIssueCommand.RegisterIssue getIssueTarget();

    void removeIssuedTarget();
}
