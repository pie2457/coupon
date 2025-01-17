package com.project.couponcore.domain.couponissue.cache;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.project.couponcore.domain.coupon.CouponReader;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponCacheServiceImpl implements CouponCacheService {
    private final CouponReader couponReader;

    @Override
    @Cacheable(cacheNames = "coupon")
    public CouponIssueCache getCachedCoupon(long couponId) {
        return new CouponIssueCache(couponReader.getCoupon(couponId));
    }
}
