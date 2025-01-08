package com.project.couponcore.domain.coupon;

import java.time.LocalDateTime;

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
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
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

    @Getter
    @AllArgsConstructor
    public enum CouponType {
        FIRST_COME_FIRST_SERVED("선착순 쿠폰");
        
        private final String description;
    }
}
