package com.project.couponcore.domain.couponissue;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.couponcore.domain.coupon.Coupon;
import com.project.couponcore.domain.coupon.CouponReader;
import com.project.couponcore.domain.couponissue.cache.event.CouponIssueCompleteEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CouponIssueServiceImpl implements CouponIssueService {
    private final CouponReader couponReader;
    private final CouponIssueStore couponIssueStore;
    private final CouponIssueReader couponIssueReader;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public CouponIssueInfo issue(CouponIssueCommand.RegisterIssue command) {
        Coupon coupon = couponReader.getCouponWithLock(command.couponId());
        couponIssueReader.checkAlreadyIssuance(coupon.getId(), command.userId());
        coupon.issue();
        CouponIssue couponIssue = couponIssueStore.store(command.toEntity());
        publishCouponEvent(coupon);
        return new CouponIssueInfo(couponIssue);
    }

    private void publishCouponEvent(Coupon coupon) {
        if (coupon.isIssueComplete()) {
            eventPublisher.publishEvent(new CouponIssueCompleteEvent(coupon.getId()));
        }
    }
}
