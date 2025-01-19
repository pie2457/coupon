package com.project.couponcore.domain.couponissue.async;

import com.project.couponcore.domain.couponissue.CouponIssueCommand;

public interface AsyncCouponIssueService {
    void issue(CouponIssueCommand.RegisterIssue command);
}
