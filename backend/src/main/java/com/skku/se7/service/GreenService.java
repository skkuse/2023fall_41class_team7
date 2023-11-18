package com.skku.se7.service;

import com.skku.se7.dto.GreenRequest;
import com.skku.se7.dto.GreenResourceResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GreenService {
    private final ViolationChecker violationChecker;
    private final JavaCodeCompiler javaCodeCompiler;
    private final JavaRunner javaRunner;
    private final HwConverter hwConverter;

    public GreenResourceResponse calculateGreen(GreenRequest greenRequest) {
        return null;
    }
}
