package com.project.couponapi.interfaces.couponissue;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.couponapi.application.couponissue.CouponIssueFacade;
import com.project.couponcore.domain.couponissue.CouponIssueCommand;
import com.project.couponcore.domain.couponissue.CouponIssueInfo;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CouponIssueApiController {
    private final CouponIssueDtoMapper mapper;
    private final CouponIssueFacade couponIssueFacade;

    @PostMapping("/issue")
    public ResponseEntity<CouponIssueDto.RegisterResponse> issue(@RequestBody CouponIssueDto.RegisterRequest request) {
        CouponIssueCommand.RegisterIssue command = mapper.of(request);
        CouponIssueInfo info = couponIssueFacade.issue(command);
        CouponIssueDto.RegisterResponse response = mapper.of(info);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
