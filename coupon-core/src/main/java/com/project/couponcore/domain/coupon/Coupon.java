package com.project.couponcore.domain.coupon;

import static lombok.AccessLevel.*;

import java.time.LocalDateTime;

import com.project.couponcore.common.exception.InvalidParamException;
import com.project.couponcore.domain.AbstractEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Table(name = "coupons")
public class Coupon extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private CouponType couponType;

    private Integer totalQuantity;

    @Column(nullable = false)
    private int issuedQuantity;

    @Column(nullable = false)
    private int discountQuantity;

    @Column(nullable = false)
    private int minAvailableQuantity;

    @Column(nullable = false)
    private LocalDateTime dataIssueStart;

    @Column(nullable = false)
    private LocalDateTime dataIssueEnd;

    public void issue() {
        if (!availableIssueQuantity()) {
            throw new InvalidParamException(
                "발급 가능한 수량을 초과 했습니다. total : %s, issued : %s".formatted(totalQuantity, issuedQuantity));
        }
        if (!availableIssueDate()) {
            throw new InvalidParamException(
                "발급 가능한 일자가 아닙니다. request : %s, issueStart : %s, issueEnd : %s"
                    .formatted(LocalDateTime.now(), dataIssueStart, dataIssueEnd));
        }
        issuedQuantity++;
    }

    private boolean availableIssueQuantity() {
        if (totalQuantity == null) {
            return true;
        }
        return totalQuantity > issuedQuantity;
    }

    private boolean availableIssueDate() {
        LocalDateTime now = LocalDateTime.now();
        return dataIssueStart.isBefore(now) && dataIssueEnd.isAfter(now);
    }

    @Getter
    @AllArgsConstructor
    public enum CouponType {
        FIRST_COME_FIRST_SERVED("선착순 쿠폰");

        private final String description;
    }
}
