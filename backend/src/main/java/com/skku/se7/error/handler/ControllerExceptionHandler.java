package com.skku.se7.error.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skku.se7.dto.*;
import com.skku.se7.error.exceptions.*;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {
    private ObjectMapper objectMapper = new ObjectMapper();
    @ExceptionHandler(BindException.class)
    public ResponseEntity<BindingExceptionResponse> bindException(BindException e, HttpServletRequest request) {
        return ResponseEntity.badRequest().body(BindingExceptionResponse.fromException(e, request));
    }

    @ExceptionHandler(WithoutProcessorException.class)
    public ResponseEntity<ExceptionResponse> withoutProcessorException(WithoutProcessorException e, HttpServletRequest request) {
        ExceptionDetail exceptionDetail = ExceptionDetail.builder()
                .field("hwSpecRequest")
                .given("none")
                .reasonMessage("cpu, gpu 둘중 하나는 반드시 포함되어야 합니다.")
                .build();
        return ResponseEntity.badRequest().body(ExceptionResponse.fromException(e, request, exceptionDetail));
    }

    @ExceptionHandler(CpuTdpWithModelNameException.class)
    public ResponseEntity<ExceptionResponse> cpuTdpWithModelNameException(CpuTdpWithModelNameException e, HttpServletRequest request) throws IOException {
        ProcessorSpecRequest data = getRequestJson(request, GreenRequest.class)
                .getHwSpecRequest().getCpuSpecRequest();

        ExceptionDetail exceptionDetail = ExceptionDetail.builder()
                .field("hwSpecRequest.cpuSpecRequest.modelName, hwSpecRequest.cpuSpecRequest.tdp")
                .given(data.getModelName() + ", " + data.getTdp())
                .reasonMessage("Cpu model명과 tdp는 배타적으로만 입력되어야 합니다.")
                .build();
        return ResponseEntity.badRequest().body(ExceptionResponse.fromException(e, request, exceptionDetail));

    }
    @ExceptionHandler(GpuTdpWithModelNameException.class)
    public ResponseEntity<ExceptionResponse> gpuTdpWithModelNameException(GpuTdpWithModelNameException e, HttpServletRequest request) throws IOException {
        ProcessorSpecRequest data = getRequestJson(request, GreenRequest.class)
                .getHwSpecRequest().getGpuSpecRequest();

        ExceptionDetail exceptionDetail = ExceptionDetail.builder()
                .field("hwSpecRequest.gpuSpecRequest.modelName, hwSpecRequest.gpuSpecRequest.tdp")
                .given(data.getModelName() + ", " + data.getTdp())
                .reasonMessage("Gpu model명과 tdp는 배타적으로만 입력되어야 합니다.")
                .build();
        return ResponseEntity.badRequest().body(ExceptionResponse.fromException(e, request, exceptionDetail));
    }

    @ExceptionHandler(NoMatchCpuModelNameException.class)
    public ResponseEntity<ExceptionResponse> noMatchCpuModelNameException(NoMatchCpuModelNameException e, HttpServletRequest request) throws IOException {
        ProcessorSpecRequest data = getRequestJson(request, GreenRequest.class)
                .getHwSpecRequest().getCpuSpecRequest();
        ExceptionDetail exceptionDetail = ExceptionDetail.builder()
                .field("hwSpecRequest.cpuSpecRequest.modelName")
                .given(data.getModelName())
                .reasonMessage("cpu Model명과 일치하는 데이터가 없습니다. Other로 보내주세요")
                .build();
        return ResponseEntity.badRequest().body(ExceptionResponse.fromException(e, request, exceptionDetail));
    }

    @ExceptionHandler(NoMatchGpuModelNameException.class)
    public ResponseEntity<ExceptionResponse> noMatchGpuModelNameException(NoMatchGpuModelNameException e, HttpServletRequest request) throws IOException {
        ProcessorSpecRequest data = getRequestJson(request, GreenRequest.class)
                .getHwSpecRequest().getGpuSpecRequest();
        ExceptionDetail exceptionDetail = ExceptionDetail.builder()
                .field("hwSpecRequest.gpuSpecRequest.modelName")
                .given(data.getModelName())
                .reasonMessage("Gpu Model명과 일치하는 데이터가 없습니다. Other로 보내주세요")
                .build();
        return ResponseEntity.badRequest().body(ExceptionResponse.fromException(e, request, exceptionDetail));
    }

    private <T> T getRequestJson(HttpServletRequest request, Class<T> jsonType) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream,
                StandardCharsets.UTF_8);
        return objectMapper.readValue(messageBody, jsonType);
    }
}