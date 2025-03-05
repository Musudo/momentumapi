package com.musadzeyt.momentumapi.dto.graphData;

import lombok.Data;

@Data
public class LineChartSeriesDto {
    private String id;
    private String label;
    private Object[] data;
}
