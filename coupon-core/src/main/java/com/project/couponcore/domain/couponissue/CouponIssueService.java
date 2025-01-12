package com.project.couponcore.domain.couponissue;

public interface CouponIssueService {
    CouponIssueInfo issue(CouponIssueCommand.RegisterIssue command);
}
