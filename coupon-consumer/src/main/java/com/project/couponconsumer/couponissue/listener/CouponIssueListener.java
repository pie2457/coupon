package com.project.couponconsumer.couponissue.listener;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.project.couponcore.domain.couponissue.CouponIssueCommand;
import com.project.couponcore.domain.couponissue.CouponIssueService;
import com.project.couponcore.domain.couponissue.cache.CouponIssueCacheService;

import lombok.RequiredArgsConstructor;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class CouponIssueListener {
    private final CouponIssueService couponIssueService;
    private final CouponIssueCacheService couponIssueCacheService;

    @Scheduled(fixedDelay = 1000L)
    public void issue() {
        while (couponIssueCacheService.existCouponIssueTarget()) {
            CouponIssueCommand.RegisterIssue target = couponIssueCacheService.getIssueTarget();
            couponIssueService.issue(target);
            couponIssueCacheService.removeIssuedTarget();
        }
    }
}
