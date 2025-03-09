package com.project.couponconsumer.couponissue.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.couponcore.domain.couponissue.CouponIssueCommand;
import com.project.couponcore.domain.couponissue.CouponIssueService;
import com.project.couponcore.domain.couponissue.cache.CouponIssueCacheService;

import lombok.RequiredArgsConstructor;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class CouponIssueListener {
    private final ObjectMapper mapper = new ObjectMapper();
    private final CouponIssueService couponIssueService;
    private final CouponIssueCacheService couponIssueCacheService;

    @Scheduled(fixedDelay = 1000L)
    public void issue() {
        while (couponIssueCacheService.existCouponIssueTarget()) {
            CouponIssueCommand.RegisterIssue target = couponIssueCacheService.getIssueTarget();
            couponIssueService.issue(target);
            couponIssueCacheService.removeIssuedTarget();
        }
    }

    @KafkaListener(topics = "coupon-issue", groupId = "coupon")
    public void issue2(ConsumerRecord<String, String> record) {
        try {
            var command = mapper.readValue(record.value(), CouponIssueCommand.RegisterIssue.class);
            couponIssueService.issue(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
