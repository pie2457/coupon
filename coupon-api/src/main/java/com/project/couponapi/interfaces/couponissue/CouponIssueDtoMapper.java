package com.project.couponapi.interfaces.couponissue;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.project.couponcore.domain.couponissue.CouponIssueCommand;
import com.project.couponcore.domain.couponissue.CouponIssueInfo;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface CouponIssueDtoMapper {
    CouponIssueCommand.RegisterIssue of(CouponIssueDto.RegisterRequest request);

    CouponIssueDto.RegisterResponse of(CouponIssueInfo info);
}
