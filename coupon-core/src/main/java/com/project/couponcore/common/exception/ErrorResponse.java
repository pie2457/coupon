package com.project.couponcore.common.exception;

public record ErrorResponse(
    int statusCode,
    String message
) {
}
