package com.project.couponcore.domain.couponissue.async;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.couponcore.common.util.DistributeLockExecutor;
import com.project.couponcore.domain.couponissue.cache.CouponCacheService;
import com.project.couponcore.domain.couponissue.cache.CouponIssueCacheService;
import com.project.couponcore.domain.couponissue.cache.CouponIssueCacheStore;

@ExtendWith(MockitoExtension.class)
class AsyncCouponIssueServiceImplTest {
    private final long userId = 1;
    private final long couponId = 1;

    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private CouponIssueCacheStore cacheStore;
    @Mock
    private CouponIssueCacheService couponIssueCacheService;
    @Mock
    private CouponCacheService couponCacheService;
    @Mock
    private DistributeLockExecutor distributeLockExecutor;
    @InjectMocks
    private AsyncCouponIssueServiceImpl issueService;
}
