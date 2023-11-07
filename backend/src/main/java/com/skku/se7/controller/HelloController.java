package com.skku.se7.controller;

import com.skku.se7.service.HelloService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HelloController {
    private final HelloService helloService;

    @GetMapping("/hello")
    public void getGreen() {
        return;
    }
}
