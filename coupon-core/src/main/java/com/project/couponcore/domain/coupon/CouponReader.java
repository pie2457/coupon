package com.project.couponcore.domain.coupon;

public interface CouponReader {
    Coupon getCoupon(long couponId);

    Coupon getCouponWithLock(long couponId);
}
