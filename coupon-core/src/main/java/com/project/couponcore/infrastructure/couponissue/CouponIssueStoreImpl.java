package com.project.couponcore.infrastructure.couponissue;

import org.springframework.stereotype.Component;

import com.project.couponcore.domain.couponissue.CouponIssue;
import com.project.couponcore.domain.couponissue.CouponIssueStore;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CouponIssueStoreImpl implements CouponIssueStore {
    private final CouponIssueRepository couponIssueRepository;

    @Override
    public CouponIssue store(CouponIssue couponIssue) {
        return couponIssueRepository.save(couponIssue);
    }
}
