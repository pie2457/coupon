package com.project.couponcore.infrastructure.couponissue;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.couponcore.domain.couponissue.CouponIssue;

public interface CouponIssueRepository extends JpaRepository<CouponIssue, Long> {
    boolean existsCouponIssuesByCouponIdAndUserId(long couponId, long userId);
}
