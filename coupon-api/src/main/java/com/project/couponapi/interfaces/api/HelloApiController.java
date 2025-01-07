package com.project.couponapi.interfaces.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloApiController {

    @GetMapping("/hello")
    public String hello() {
        return "hello!";
    }
}
