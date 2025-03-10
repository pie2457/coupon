package com.project.couponcore.domain.couponissue.cache;

import com.project.couponcore.domain.couponissue.CouponIssueCommand;

public interface CouponIssueCacheStore {
    Long sAdd(String key, String value);

    Long rPush(String key, String value);

    void validateIssueRequest(CouponIssueCommand.RegisterIssue command, int totalIssueQuantity);

    String lPop(String key);
}
