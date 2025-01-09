package com.project.couponcore.domain.couponissue;

import lombok.Getter;

@Getter
public class CouponIssueInfo {
    private final long couponIssueId;

    public CouponIssueInfo(CouponIssue couponIssue) {
        this.couponIssueId = couponIssue.getId();
    }
}
