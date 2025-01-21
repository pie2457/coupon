package com.project.couponapi.application.couponissue;

import org.springframework.stereotype.Service;

import com.project.couponcore.domain.couponissue.CouponIssueCommand;
import com.project.couponcore.domain.couponissue.async.AsyncCouponIssueService;
import com.project.couponcore.domain.couponissue.async.AsyncCouponIssueService2;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponIssueFacade {
    private final AsyncCouponIssueService couponIssueService1;
    private final AsyncCouponIssueService2 couponIssueService2;

    public void issueV1(CouponIssueCommand.RegisterIssue command) {
        couponIssueService1.issue(command);
    }

    public void issueV2(CouponIssueCommand.RegisterIssue command) {
        couponIssueService2.issue(command);
    }
}
