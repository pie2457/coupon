package com.project.couponapi.application.couponissue;

import org.springframework.stereotype.Service;

import com.project.couponcore.domain.couponissue.CouponIssueCommand;
import com.project.couponcore.domain.couponissue.CouponIssueInfo;
import com.project.couponcore.domain.couponissue.CouponIssueService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponIssueFacade {
    private final CouponIssueService couponIssueService;

    public CouponIssueInfo issue(CouponIssueCommand.RegisterIssue command) {
        return couponIssueService.issue(command);
    }
}
