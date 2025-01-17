package com.project.couponapi.interfaces.couponissue;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.project.couponcore.domain.couponissue.CouponIssueCommand;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface CouponIssueDtoMapper {
    CouponIssueCommand.RegisterIssue of(CouponIssueDto.RegisterRequest request);
}
