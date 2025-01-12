package com.project.couponapi.interfaces.couponissue;

public class CouponIssueDto {

    public record RegisterRequest(
        long userId,
        long couponId
    ) {

    }

    public record RegisterResponse(
        long couponIssueId
    ) {

    }
}
