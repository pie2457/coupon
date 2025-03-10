package com.project.couponcore.infrastructure.couponissue.redis;

import java.util.List;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import com.project.couponcore.common.util.CacheKeyGenerator;
import com.project.couponcore.domain.couponissue.CouponIssueCommand;
import com.project.couponcore.domain.couponissue.cache.CouponIssueCacheStore;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponIssueRedisStoreImpl implements CouponIssueCacheStore {
    private final RedisTemplate<String, String> redisTemplate;
    private final RedisScript<String> issueScript = issueRequestScript();

    @Override
    public Long sAdd(String key, String value) {
        return redisTemplate.opsForSet().add(key, value);
    }

    @Override
    public Long rPush(String key, String value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    @Override
    public String lPop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    @Override
    public void validateIssueRequest(CouponIssueCommand.RegisterIssue command, int totalIssueQuantity) {
        String issueRequestKey = CacheKeyGenerator.getIssueRequestKey(command.couponId());

        String code = redisTemplate.execute(
            issueScript,
            List.of(issueRequestKey),                     // KEYS[1]
            String.valueOf(command.userId()),             // ARGV[1]
            String.valueOf(totalIssueQuantity));          // ARGV[2]
        CouponIssueRequestCode.checkRequestResult(CouponIssueRequestCode.find(code));
    }

    private RedisScript<String> issueRequestScript() {
        String script = """
            if redis.call('SISMEMBER', KEYS[1], ARGV[1]) == 1 then
                return '2'
            end
                            
            if tonumber(ARGV[2]) > redis.call('SCARD', KEYS[1]) then
                redis.call('SADD', KEYS[1], ARGV[1])
                return '1'
            end
                            
            return '3'
            """;
        return RedisScript.of(script, String.class);
    }
}
