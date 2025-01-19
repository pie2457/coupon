package com.project.couponcore.domain.couponissue.cache;

public interface CouponIssueCacheReader {
    Long sCard(String key);

    Boolean sIsMember(String key, String value);
}
