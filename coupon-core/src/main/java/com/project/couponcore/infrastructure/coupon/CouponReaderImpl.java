package com.project.couponcore.infrastructure.coupon;

import org.springframework.stereotype.Component;

import com.project.couponcore.common.exception.EntityNotFoundException;
import com.project.couponcore.domain.coupon.Coupon;
import com.project.couponcore.domain.coupon.CouponReader;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CouponReaderImpl implements CouponReader {
    private final CouponRepository couponRepository;

    @Override
    public Coupon getCoupon(long couponId) {
        return couponRepository.findById(couponId)
            .orElseThrow(() -> new EntityNotFoundException("쿠폰 정책이 존재하지 않습니다. %s".formatted(couponId)));
    }
}
