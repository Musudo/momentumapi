package com.musadzeyt.momentumapi.dto.statDto;

import com.musadzeyt.momentumapi.enums.stat.StackOrderEnum;
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
    private StackOrderEnum stackOrder = StackOrderEnum.ASCENDING;
}
