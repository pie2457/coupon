package com.project.couponcore.domain.couponissue.async;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.project.couponcore.common.exception.EntityNotFoundException;
import com.project.couponcore.domain.couponissue.CouponIssueCommand;
import com.project.couponcore.domain.couponissue.cache.CouponCacheService;
import com.project.couponcore.domain.couponissue.cache.CouponIssueCache;
import com.project.couponcore.domain.couponissue.cache.CouponIssueCacheStore;

@ExtendWith(MockitoExtension.class)
class AsyncCouponIssueServiceImplTest {
    private final long userId = 1;
    private final long couponId = 1;

    @Mock
    private CouponCacheService cacheService;
    @Mock
    private CouponIssueCacheStore cacheStore;
    @InjectMocks
    private AsyncCouponIssueServiceImpl2 issueService;

    @Test
    @DisplayName("쿠폰 발급에 성공한다.")
    void whenRegisterCouponIssue_thenSuccess() {
        // given
        CouponIssueCommand.RegisterIssue command = new CouponIssueCommand.RegisterIssue(userId, couponId);
        CouponIssueCache cacheCoupon = Mockito.mock(CouponIssueCache.class);

        given(cacheService.getCachedCoupon(couponId)).willReturn(cacheCoupon);
        willDoNothing().given(cacheCoupon).checkIssuableCoupon();

        // when
        issueService.issue(command);

        // then
        then(cacheService).should(times(1)).getCachedCoupon(couponId);
        then(cacheCoupon).should(times(1)).checkIssuableCoupon();
        then(cacheStore).should(times(1)).validateIssueRequest(command, cacheCoupon.totalQuantity());
    }

    @Test
    @DisplayName("존재하지 않는 쿠폰 발급 요청시 예외가 발생한다.")
    void givenNotExistCoupon_whenRegisterCouponIssue_thenThrowsException() {
        // given
        CouponIssueCommand.RegisterIssue command = new CouponIssueCommand.RegisterIssue(userId, couponId);

        willThrow(EntityNotFoundException.class)
            .given(cacheService)
            .getCachedCoupon(couponId);

        // when & then
        assertThatThrownBy(() -> issueService.issue(command))
            .isInstanceOf(EntityNotFoundException.class);
    }
}
