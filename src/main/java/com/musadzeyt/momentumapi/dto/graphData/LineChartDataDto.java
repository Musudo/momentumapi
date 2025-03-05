package com.musadzeyt.momentumapi.dto.graphData;

import lombok.Data;

import java.util.List;

@Data
public class LineChartDataDto {
    private String title;
    private int value;
    private String caption;
    private String trend;
    private List<LineChartSeriesDto> seriesDtos;
    private int year;
    private int month;
}
