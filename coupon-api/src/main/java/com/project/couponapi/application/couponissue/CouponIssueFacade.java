package com.project.couponapi.application.couponissue;

import org.springframework.stereotype.Service;

import com.project.couponapi.common.util.DistributeLockExecutor;
import com.project.couponcore.domain.couponissue.CouponIssueCommand;
import com.project.couponcore.domain.couponissue.CouponIssueService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponIssueFacade {
    private final CouponIssueService couponIssueService;
    private final DistributeLockExecutor distributeLockExecutor;

    public void issue(CouponIssueCommand.RegisterIssue command) {
        distributeLockExecutor.execute("lock_" + command.couponId(), 10000, 10000,
            () -> {
                couponIssueService.issue(command);
            });
    }
}
