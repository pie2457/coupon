package com.project.couponcore.domain.couponissue;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.project.couponcore.common.exception.BadRequestException;
import com.project.couponcore.common.exception.EntityNotFoundException;
import com.project.couponcore.common.exception.InvalidParamException;
import com.project.couponcore.common.response.ErrorCode;
import com.project.couponcore.domain.coupon.Coupon;
import com.project.couponcore.domain.coupon.CouponReader;

@ExtendWith(MockitoExtension.class)
class CouponIssueServiceImplTest {
    private final long userId = 1;
    private final long couponId = 1;

    @Mock
    private CouponReader couponReader;
    @Mock
    private CouponIssueStore couponIssueStore;
    @Mock
    private CouponIssueReader couponIssueReader;
    @InjectMocks
    private CouponIssueServiceImpl couponIssueService;

    @Test
    @DisplayName("쿠폰 발급에 성공한다.")
    void whenRegisterCouponIssue_thenSuccess() {
        // given
        CouponIssueCommand.RegisterIssue command = new CouponIssueCommand.RegisterIssue(userId, couponId);
        Coupon coupon = createCoupon(1000, 0);
        CouponIssue couponIssue = Mockito.mock(CouponIssue.class);

        given(couponIssue.getId()).willReturn(1L);
        given(couponReader.getCoupon(couponId)).willReturn(coupon);
        willDoNothing().given(couponIssueReader).checkAlreadyIssuance(couponId, userId);
        given(couponIssueStore.store(any())).willReturn(couponIssue);

        // when
        couponIssueService.issue(command);

        // then
        then(couponReader).should(times(1)).getCoupon(command.couponId());
        then(couponIssueReader).should(times(1))
            .checkAlreadyIssuance(command.couponId(), command.userId());
        then(couponIssueStore).should(times(1)).store(any());
    }

    @Test
    @DisplayName("쿠폰 발급 내역이 존재하면 예외를 반환한다.")
    void givenAlreadyExistCouponIssue_whenRegisterCouponIssue_thenThrowsException() {
        // given
        CouponIssueCommand.RegisterIssue command = new CouponIssueCommand.RegisterIssue(userId, couponId);
        Coupon coupon = createCoupon(1000, 0);

        given(couponReader.getCoupon(couponId)).willReturn(coupon);
        willThrow(BadRequestException.class)
            .given(couponIssueReader)
            .checkAlreadyIssuance(couponId, userId);

        // when & then
        assertThatThrownBy(() -> couponIssueService.issue(command))
            .isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("발급 수량이 초과되면 예외가 발생한다.")
    void givenIssuanceQuantityExceeded_whenRegisterCouponIssue_thenThrowsException() {
        CouponIssueCommand.RegisterIssue command = new CouponIssueCommand.RegisterIssue(userId, couponId);
        Coupon coupon = createCoupon(10, 11);

        given(couponReader.getCoupon(couponId)).willReturn(coupon);
        willDoNothing().given(couponIssueReader).checkAlreadyIssuance(couponId, userId);

        // when & then
        assertThatThrownBy(() -> couponIssueService.issue(command))
            .isInstanceOf(InvalidParamException.class)
            .extracting("errorCode").isEqualTo(ErrorCode.INVALID_PARAMETER);
    }

    @Test
    @DisplayName("존재하지 않는 쿠폰 발급 요청시 예외가 발생한다.")
    void givenNotExistCoupon_whenRegisterCouponIssue_thenThrowsException() {
        CouponIssueCommand.RegisterIssue command = new CouponIssueCommand.RegisterIssue(userId, couponId);

        willThrow(EntityNotFoundException.class)
            .given(couponReader)
            .getCoupon(couponId);

        // when & then
        assertThatThrownBy(() -> couponIssueService.issue(command))
            .isInstanceOf(EntityNotFoundException.class);
    }

    private Coupon createCoupon(int totalQuantity, int issuedQuantity) {
        Coupon coupon = Coupon.builder()
            .id(1L)
            .title("블랙프라이데이 쿠폰")
            .couponType(Coupon.CouponType.FIRST_COME_FIRST_SERVED)
            .totalQuantity(totalQuantity)
            .issuedQuantity(issuedQuantity)
            .discountAmount(20)
            .minAvailableAmount(10000)
            .dateIssueStart(LocalDateTime.now().minusDays(2))
            .dateIssueEnd(LocalDateTime.now().plusDays(2))
            .build();
        return coupon;
    }
}
