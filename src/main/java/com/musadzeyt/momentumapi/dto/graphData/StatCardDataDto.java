package com.musadzeyt.momentumapi.dto.graphData;

import lombok.Data;

@Data
public class StatCardDataDto {
    private String title;
    private int value;
    private String caption;
    private String trend;
    private Object[] data;
    private int year;
    private int month;
}
