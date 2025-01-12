package com.project.couponcore.infrastructure.coupon;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.couponcore.domain.coupon.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
