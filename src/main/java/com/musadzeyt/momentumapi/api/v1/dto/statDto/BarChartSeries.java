package com.musadzeyt.momentumapi.api.v1.dto.statDto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BarChartSeries {
    private String id;
    private String label;
    private List<Integer> data;
    @Builder.Default
    private String stack = "A";
}
