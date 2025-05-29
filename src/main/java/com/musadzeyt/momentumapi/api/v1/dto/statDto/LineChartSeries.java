package com.musadzeyt.momentumapi.api.v1.dto.statDto;

import com.musadzeyt.momentumapi.enums.statEnums.StackOrder;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LineChartSeries {
    private String id;
    private String label;
    private List<Integer> data;
    @Builder.Default
    private boolean showMark = false;
    @Builder.Default
    private String curve = "linear";
    @Builder.Default
    private String stack = "total";
    @Builder.Default
    private boolean area = true;
    @Builder.Default
    private StackOrder stackOrder = StackOrder.ASCENDING;
}
