package com.project.couponcore.domain.couponissue.async;

import org.springframework.stereotype.Service;

import com.project.couponcore.domain.couponissue.CouponIssueCommand;
import com.project.couponcore.domain.couponissue.cache.CouponCacheService;
import com.project.couponcore.domain.couponissue.cache.CouponIssueCache;
import com.project.couponcore.domain.couponissue.cache.CouponIssueCacheStore;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AsyncCouponIssueServiceImpl2 implements AsyncCouponIssueService2 {
    private final CouponCacheService couponCacheService;
    private final CouponIssueCacheStore couponIssueCacheStore;

    public void issue(CouponIssueCommand.RegisterIssue command) {
        CouponIssueCache coupon = couponCacheService.getLocalCachedCoupon(command.couponId());
        coupon.checkIssuableCoupon();
        issueRequest(command, coupon.totalQuantity());
    }

    private void issueRequest(CouponIssueCommand.RegisterIssue command, Integer totalIssueQuantity) {
        if (totalIssueQuantity == null) {
            couponIssueCacheStore.issueRequest(command, Integer.MAX_VALUE);
        }
        couponIssueCacheStore.issueRequest(command, totalIssueQuantity);
    }
}
