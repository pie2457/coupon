package com.project.couponcore.domain.couponissue.cache;

import org.springframework.stereotype.Service;

import com.project.couponcore.common.exception.BadRequestException;
import com.project.couponcore.common.exception.InvalidParamException;
import com.project.couponcore.common.response.ErrorCode;
import com.project.couponcore.common.util.CacheKeyGenerator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponIssueCacheServiceImpl implements CouponIssueCacheService {
    private final CouponIssueCacheReader cacheReader;

    @Override
    public void checkCouponIssueQuantity(CouponIssueCache couponIssueCache, long userId) {
        if (!availableTotalIssueQuantity(couponIssueCache.totalQuantity(), couponIssueCache.id())) {
            throw new InvalidParamException(
                "발급 가능한 수량을 초과 했습니다. total : %s, couponId : %s".formatted(couponIssueCache.totalQuantity(),
                    couponIssueCache.id()));
        }
        if (availableUserIssueQuantity(couponIssueCache.id(), userId)) {
            throw new BadRequestException(ErrorCode.DUPLICATED_ENTITY,
                "이미 발급된 쿠폰입니다. userId : %d, couponId : %d".formatted(userId, couponIssueCache.id()));
        }
    }

    private boolean availableTotalIssueQuantity(Integer totalQuantity, long couponId) {
        if (totalQuantity == null) {
            return true;
        }
        String key = CacheKeyGenerator.getIssueRequestKey(couponId);
        return totalQuantity > cacheReader.sCard(key);
    }

    private boolean availableUserIssueQuantity(long couponId, long userId) {
        String key = CacheKeyGenerator.getIssueRequestKey(couponId);
        return cacheReader.sIsMember(key, String.valueOf(userId));
    }
}
