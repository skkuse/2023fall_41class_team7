package com.skku.se7.controller;

import com.skku.se7.controller.validator.EnvironmentValidator;
import com.skku.se7.controller.validator.HwValidator;
import com.skku.se7.dto.GreenRequest;
import com.skku.se7.dto.GreenResourceResponse;
import com.skku.se7.service.GreenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class GreenController {
    private final GreenService greenService;
    private final HwValidator hwValidator;
    private final EnvironmentValidator environmentValidator;

    @PostMapping("/green")
    public GreenResourceResponse getGreen(@RequestBody GreenRequest greenRequest) {
        log.info("greenRequest : {}", greenRequest);
        log.info("hwSpecRequest.getCpuSpecRequest().get().getCoreNumber() : {}", greenRequest.getHwSpecRequest().getCpuSpecRequest().getCoreNumber());
        return null;
    }

}
