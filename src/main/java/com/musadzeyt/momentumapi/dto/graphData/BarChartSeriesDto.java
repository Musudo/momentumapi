package com.musadzeyt.momentumapi.dto.graphData;

import lombok.Data;

@Data
public class BarChartSeriesDto {
    private String id;
    private String label;
    private Object[] data;
}
