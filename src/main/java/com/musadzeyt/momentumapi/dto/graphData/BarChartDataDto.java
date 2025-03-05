package com.musadzeyt.momentumapi.dto.graphData;

import lombok.Data;

import java.util.List;

@Data
public class BarChartDataDto {
    private String title;
    private int value;
    private String caption;
    private List<BarChartSeriesDto> barChartSeriesDtos;
}
