package com.project.couponcore.common.util;

public class CacheKeyGenerator {

    public static String getIssueRequestKey(long couponId) {
        return "issue.request.coponId = %s".formatted(couponId);
    }

    public static String getIssueRequestQueueKey() {
        return "issue.request";
    }
}
