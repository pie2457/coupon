package com.project.couponcore.domain.couponissue.cache;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.couponcore.common.exception.BadRequestException;
import com.project.couponcore.common.exception.EntityNotFoundException;
import com.project.couponcore.common.exception.InvalidParamException;
import com.project.couponcore.common.response.ErrorCode;
import com.project.couponcore.common.util.CacheKeyGenerator;
import com.project.couponcore.domain.couponissue.CouponIssueCommand;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponIssueCacheServiceImpl implements CouponIssueCacheService {
    private final CouponIssueCacheReader cacheReader;
    private final CouponIssueCacheStore cacheStore;
    private final ObjectMapper mapper = new ObjectMapper();

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

    @Override
    public boolean existCouponIssueTarget() {
        return cacheReader.lSize(getIssueRequestQueueKey()) > 0;
    }

    @Override
    public CouponIssueCommand.RegisterIssue getIssueTarget() {
        try {
            return mapper.readValue(
                cacheReader.lIndex(getIssueRequestQueueKey(), 0), CouponIssueCommand.RegisterIssue.class);
        } catch (JsonProcessingException e) {
            throw new EntityNotFoundException("해당 요청을 찾을 수 없습니다.");
        }
    }

    @Override
    public void removeIssuedTarget() {
        cacheStore.lPop(getIssueRequestQueueKey());
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

    private String getIssueRequestQueueKey() {
        return CacheKeyGenerator.getIssueRequestQueueKey();
    }

    private String getIssueRequestKey(CouponIssueCommand.RegisterIssue command) {
        return CacheKeyGenerator.getIssueRequestKey(command.couponId());
    }
}
