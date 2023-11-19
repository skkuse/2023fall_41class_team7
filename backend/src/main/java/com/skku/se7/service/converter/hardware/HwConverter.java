package com.skku.se7.service.converter.hardware;

import com.skku.se7.service.dataHandler.ProcessorTdpHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class HwConverter {
    private final ProcessorTdpHandler processorTdpHandler;

}
