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
    public ResponseEntity<Void> issue(@RequestBody CouponIssueDto.RegisterRequest request) {
        CouponIssueCommand.RegisterIssue command = mapper.of(request);
        couponIssueFacade.issue(command);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
