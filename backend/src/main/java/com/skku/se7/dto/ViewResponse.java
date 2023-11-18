package com.skku.se7.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViewResponse<T> {
    private Integer count;
    private List<T> data;

    public ViewResponse(List<T> data) {
        count = data.size();
        this.data = data;
    }
}
