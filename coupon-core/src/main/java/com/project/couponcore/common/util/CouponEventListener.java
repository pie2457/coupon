package com.project.couponcore.common.util;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.project.couponcore.domain.couponissue.cache.CouponCacheService;
import com.project.couponcore.domain.couponissue.cache.event.CouponIssueCompleteEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CouponEventListener {
    private final CouponCacheService cacheService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    void issueComplete(CouponIssueCompleteEvent event) {
        log.info("issue complete. cache refresh start..");
        cacheService.putCouponCache(event.couponId());
        cacheService.putCouponLocalCache(event.couponId());
        log.info("issue complete. cache refresh end..");
    }
}
