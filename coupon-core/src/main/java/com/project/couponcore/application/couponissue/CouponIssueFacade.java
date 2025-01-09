package com.project.couponcore.application.couponissue;

import org.springframework.stereotype.Service;

import com.project.couponcore.domain.couponissue.CouponIssueCommand;
import com.project.couponcore.domain.couponissue.CouponIssueService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponIssueFacade {
    private final CouponIssueService couponIssueService;

    public void issue(CouponIssueCommand.RegisterIssue command) {
        couponIssueService.issue(command);
    }
}
