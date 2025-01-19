package com.project.couponcore.domain.couponissue.cache;

public interface CouponIssueCacheStore {
    Long sAdd(String key, String value);

    Long rPush(String key, String value);
}
