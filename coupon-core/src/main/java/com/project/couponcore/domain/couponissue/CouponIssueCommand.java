package com.project.couponcore.domain.couponissue;

public class CouponIssueCommand {

    public record RegisterIssue(
        long userId,
        long couponId
    ) {
        public CouponIssue toEntity() {
            return CouponIssue.builder()
                .userId(userId)
                .couponId(couponId)
                .build();
        }
    }
}
