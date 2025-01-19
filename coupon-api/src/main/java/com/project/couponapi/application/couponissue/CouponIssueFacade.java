package com.project.couponapi.application.couponissue;

import org.springframework.stereotype.Service;

import com.project.couponcore.domain.couponissue.CouponIssueCommand;
import com.project.couponcore.domain.couponissue.async.AsyncCouponIssueService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponIssueFacade {
    private final AsyncCouponIssueService couponIssueService;

    public void issue(CouponIssueCommand.RegisterIssue command) {
        couponIssueService.issue(command);
    }
}
