package com.skku.se7.dto;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {
    private String url;
    private String exception;
    private ExceptionDetail errorDetail;

    public static ExceptionResponse fromException(Exception e, HttpServletRequest request, ExceptionDetail errorDetail) {
        return ExceptionResponse.builder()
                .url(request.getRequestURI())
                .exception(e.getClass().getName())
                .errorDetail(errorDetail)
                .build();
    }
}

