package com.musadzeyt.momentumapi.api.v1.dto.statDto;

import com.musadzeyt.momentumapi.enums.statEnums.Trend;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class BarChart {
    private String title;
    private int value;
    private String caption;
    private Trend trend;
    private List<BarChartSeries> series;
    private List<String> months;
    @Builder.Default
    private int borderRadius = 8;
    @Builder.Default
    private int height = 250;
    @Builder.Default
    private Map<String, Integer> margin = Map.of(
            "left", 50,
            "right", 0,
            "top", 20,
            "bottom", 20
    );
    @Builder.Default
    private Map<String, Boolean> grid = Map.of("horizontal", true);
    @Builder.Default
    private Map<String, Map<String, Boolean>> slotProp = Map.of(
            "legend", Map.of("hidden", true)
    );
}
