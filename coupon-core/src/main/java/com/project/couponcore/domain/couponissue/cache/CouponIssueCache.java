package com.project.couponcore.domain.couponissue.cache;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.project.couponcore.common.exception.BadRequestException;
import com.project.couponcore.common.exception.InvalidParamException;
import com.project.couponcore.common.response.ErrorCode;
import com.project.couponcore.domain.coupon.Coupon;

public record CouponIssueCache(
    Long id,
    Coupon.CouponType couponType,
    Integer totalQuantity,
    boolean availableIssueQuantity,
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    LocalDateTime dateIssueStart,
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    LocalDateTime dateIssueEnd
) {
    public CouponIssueCache(Coupon coupon) {
        this(
            coupon.getId(),
            coupon.getCouponType(),
            coupon.getTotalQuantity(),
            coupon.availableIssueQuantity(),
            coupon.getDateIssueStart(),
            coupon.getDateIssueEnd()
        );
    }

    public void checkIssuableCoupon() {
        if (!availableIssueQuantity) {
            throw new BadRequestException(ErrorCode.FAIL_COUPON_ISSUE_REQUEST, "모든 발급 수량이 소진되었습니다.");
        }
        if (!availableIssueDate()) {
            throw new InvalidParamException("발급 가능한 일자가 아닙니다. request : %s, issueStart : %s, issueEnd : %s"
                .formatted(LocalDateTime.now(), dateIssueStart, dateIssueEnd));
        }
    }

    private boolean availableIssueDate() {
        LocalDateTime now = LocalDateTime.now();
        return dateIssueStart.isBefore(now) && dateIssueEnd.isAfter(now);
    }
}
