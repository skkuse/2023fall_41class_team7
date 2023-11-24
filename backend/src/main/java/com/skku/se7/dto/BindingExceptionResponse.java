package com.skku.se7.dto;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BindingExceptionResponse {
    private String url;
    private String exception;
    @Builder.Default
    private List<ExceptionDetail> errorDetails = new ArrayList<>();

    public static BindingExceptionResponse fromException(BindException bindException, HttpServletRequest request) {
        List<ExceptionDetail> errorDetails = bindException.getBindingResult().getFieldErrors().stream()
                .map(ExceptionDetail::new)
                .collect(Collectors.toList());
        return BindingExceptionResponse.builder()
                .errorDetails(errorDetails)
                .exception(bindException.getClass().getName())
                .url(request.getRequestURI())
                .build();
    }
}