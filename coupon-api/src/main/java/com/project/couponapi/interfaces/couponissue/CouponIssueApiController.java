package com.project.couponapi.interfaces.couponissue;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.couponapi.application.couponissue.CouponIssueFacade;
import com.project.couponcore.domain.couponissue.CouponIssueCommand;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CouponIssueApiController {
    private final CouponIssueDtoMapper mapper;
    private final CouponIssueFacade couponIssueFacade;

    @PostMapping("/v1/issue")
    public ResponseEntity<Void> issueV1(@RequestBody CouponIssueDto.RegisterRequest request) {
        CouponIssueCommand.RegisterIssue command = mapper.of(request);
        couponIssueFacade.issueV1(command);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/v2/issue")
    public ResponseEntity<Void> issueV2(@RequestBody CouponIssueDto.RegisterRequest request) {
        CouponIssueCommand.RegisterIssue command = mapper.of(request);
        couponIssueFacade.issueV2(command);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
