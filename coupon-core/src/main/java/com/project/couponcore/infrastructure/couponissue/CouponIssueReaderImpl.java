package com.project.couponcore.infrastructure.couponissue;

import org.springframework.stereotype.Component;

import com.project.couponcore.common.exception.BadRequestException;
import com.project.couponcore.common.response.ErrorCode;
import com.project.couponcore.domain.couponissue.CouponIssueReader;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CouponIssueReaderImpl implements CouponIssueReader {
    private final CouponIssueRepository couponIssueRepository;

    @Override
    public void checkAlreadyIssuance(long couponId, long userId) {
        if (couponIssueRepository.existsCouponIssuesByCouponIdAndUserId(couponId, userId)) {
            throw new BadRequestException(
                ErrorCode.DUPLICATED_ENTITY,
                "이미 발급된 쿠폰입니다. userId : %d, couponId : %d".formatted(userId, couponId));
        }
    }
}
