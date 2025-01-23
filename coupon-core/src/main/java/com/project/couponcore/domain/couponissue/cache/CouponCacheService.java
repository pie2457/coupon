package com.project.couponcore.domain.couponissue.cache;

public interface CouponCacheService {
    CouponIssueCache getCachedCoupon(long couponId);

    CouponIssueCache getLocalCachedCoupon(long couponId);

    CouponIssueCache putCouponCache(long couponId);

    CouponIssueCache putCouponLocalCache(long couponId);
}
