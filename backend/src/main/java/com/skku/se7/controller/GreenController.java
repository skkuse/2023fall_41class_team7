package com.skku.se7.controller;

import com.skku.se7.dto.GreenResourceResponse;
import com.skku.se7.dto.HwSpecRequest;
import com.skku.se7.dto.RunningEnvironmentRequest;
import com.skku.se7.service.GreenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class GreenController {
    private final GreenService greenService;

    @PostMapping("/green")
    public GreenResourceResponse getGreen(@ModelAttribute HwSpecRequest hwSpecRequest, @ModelAttribute RunningEnvironmentRequest runningEnvironmentRequest, @RequestParam String javaCode) {
        return;
    }

}
