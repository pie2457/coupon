package com.project.couponcore.infrastructure.couponissue.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.project.couponcore.domain.couponissue.cache.CouponIssueCacheStore;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponIssueCacheStoreImpl implements CouponIssueCacheStore {
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public Long sAdd(String key, String value) {
        return redisTemplate.opsForSet().add(key, value);
    }

    @Override
    public Long rPush(String key, String value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }
}
