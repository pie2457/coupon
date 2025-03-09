package com.project.couponcore.domain.couponissue.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.couponcore.common.exception.BadRequestException;
import com.project.couponcore.common.response.ErrorCode;
import com.project.couponcore.domain.couponissue.CouponIssueCommand;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponIssueProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper mapper = new ObjectMapper();

    public void sendIssueRequest(CouponIssueCommand.RegisterIssue command) {
        try {
            String json = mapper.writeValueAsString(command);
            kafkaTemplate.send("coupon-issue", json);
        } catch (JsonProcessingException e) {
            throw new BadRequestException(ErrorCode.FAIL_COUPON_ISSUE_REQUEST);
        }
    }
}
