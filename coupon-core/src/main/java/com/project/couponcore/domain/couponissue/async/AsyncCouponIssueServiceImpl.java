package com.project.couponcore.domain.couponissue.async;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.couponcore.common.exception.BadRequestException;
import com.project.couponcore.common.response.ErrorCode;
import com.project.couponcore.common.util.CacheKeyGenerator;
import com.project.couponcore.common.util.DistributeLockExecutor;
import com.project.couponcore.domain.couponissue.CouponIssueCommand;
import com.project.couponcore.domain.couponissue.cache.CouponCacheService;
import com.project.couponcore.domain.couponissue.cache.CouponIssueCache;
import com.project.couponcore.domain.couponissue.cache.CouponIssueCacheService;
import com.project.couponcore.domain.couponissue.cache.CouponIssueCacheStore;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AsyncCouponIssueServiceImpl implements AsyncCouponIssueService {
    private final ObjectMapper objectMapper;
    private final CouponIssueCacheStore cacheStore;
    private final CouponIssueCacheService couponIssueCacheService;
    private final CouponCacheService couponCacheService;
    private final DistributeLockExecutor distributeLockExecutor;

    @Override
    public void issue(CouponIssueCommand.RegisterIssue command) {
        CouponIssueCache coupon = couponCacheService.getCachedCoupon(command.couponId());
        coupon.checkIssuableCoupon();
        distributeLockExecutor.execute("lock_%s".formatted(command.couponId()), 3000, 3000,
            () -> {
                couponIssueCacheService.checkCouponIssueQuantity(coupon, command.userId());
                issueRequest(command);
            });
    }

    private void issueRequest(CouponIssueCommand.RegisterIssue command) {
        try {
            String value = objectMapper.writeValueAsString(command);
            cacheStore.sAdd(CacheKeyGenerator.getIssueRequestKey(command.couponId()), String.valueOf(command.userId()));
            cacheStore.rPush(CacheKeyGenerator.getIssueRequestQueueKey(), value);
        } catch (JsonProcessingException e) {
            throw new BadRequestException(ErrorCode.FAIL_COUPON_ISSUE_REQUEST);
        }
    }
}
