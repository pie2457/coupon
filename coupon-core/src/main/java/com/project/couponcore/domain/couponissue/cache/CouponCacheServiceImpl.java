package com.project.couponcore.domain.couponissue.cache;

import org.springframework.aop.framework.AopContext;
import org.springframework.cache.annotation.CachePut;
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

    @Override
    @CachePut(cacheNames = "coupon")
    public CouponIssueCache putCouponCache(long couponId) {
        return getCachedCoupon(couponId);
    }

    @Override
    @Cacheable(cacheNames = "coupon", cacheManager = "localCacheManager")
    public CouponIssueCache getLocalCachedCoupon(long couponId) {
        return proxy().getCachedCoupon(couponId);
    }

    @Override
    @CachePut(cacheNames = "coupon", cacheManager = "localCacheManager")
    public CouponIssueCache putCouponLocalCache(long couponId) {
        return getLocalCachedCoupon(couponId);
    }

    private CouponCacheServiceImpl proxy() {
        return ((CouponCacheServiceImpl)AopContext.currentProxy());
    }
}
