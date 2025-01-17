package com.project.couponcore.infrastructure.couponissue.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.project.couponcore.domain.couponissue.cache.CouponIssueCacheReader;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CouponIssueCacheReaderImpl implements CouponIssueCacheReader {
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public Long sCard(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    @Override
    public Boolean sIsMember(String key, String value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }
}
