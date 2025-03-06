package com.musadzeyt.momentumapi.dto.stat;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class LineChartDto {
    private String title;
    private int value;
    private String caption;
    private List<LineChartSeriesDto> series;
    private List<String> months;
    @Builder.Default
    private boolean legendHidden = true;
    @Builder.Default
    private int height = 250;
    @Builder.Default
    private Map<String, Integer> margin = Map.of(
            "left", 50,
            "right", 20,
            "top", 20,
            "bottom", 20
    );
    @Builder.Default
    private Map<String, Boolean> grid = Map.of("horizontal", true);
}
