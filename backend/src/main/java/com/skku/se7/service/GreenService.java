package com.skku.se7.service;

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
    private final ResourceProcessor resourceProcessor;

    public void calculateGreen(String javaCodeInput) {

    }
}
