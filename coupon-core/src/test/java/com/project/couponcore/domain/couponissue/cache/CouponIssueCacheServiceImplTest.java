package com.project.couponcore.domain.couponissue.cache;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.couponcore.common.exception.EntityNotFoundException;
import com.project.couponcore.domain.couponissue.CouponIssueCommand;

@ExtendWith(MockitoExtension.class)
class CouponIssueCacheServiceImplTest {

    @Mock
    private CouponIssueCacheReader cacheReader;
    @InjectMocks
    private CouponIssueCacheServiceImpl couponIssueCacheService;
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("쿠폰 발급 대상을 캐시 큐에서 추출하는 것에 성공한다.")
    void whenGetIssueTarget_thenSuccess() throws JsonProcessingException {
        // given
        long userId = 1;
        long couponId = 1;

        CouponIssueCommand.RegisterIssue command = new CouponIssueCommand.RegisterIssue(userId, couponId);
        given(cacheReader.lIndex(anyString(), anyLong())).willReturn(mapper.writeValueAsString(command));

        // when
        couponIssueCacheService.getIssueTarget();

        // then
        then(cacheReader).should(times(1)).lIndex(anyString(), anyLong());
    }

    @Test
    @DisplayName("캐시에 유효하지 않은 캐시값이 존재해 쿠폰 발급 대상 추출에 실패한다.")
    void givenInvalidCacheValue_whenGetIssueTarget_thenThrowsException() {
        // given
        given(cacheReader.lIndex(anyString(), anyLong())).willReturn("abcd");

        // when & then
        assertThatThrownBy(() -> couponIssueCacheService.getIssueTarget())
            .isInstanceOf(EntityNotFoundException.class);
    }
}
