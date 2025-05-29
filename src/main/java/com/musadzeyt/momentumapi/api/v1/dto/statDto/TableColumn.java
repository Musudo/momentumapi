package com.musadzeyt.momentumapi.api.v1.dto.statDto;

import com.musadzeyt.momentumapi.enums.statEnums.ColumnName;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class TableColumn {
    private String field;
    private ColumnName headerName;
    private int minWidth;
    @Builder.Default
    private String headerAlign = "right";
    @Builder.Default
    private String align = "right";
    @Builder.Default
    private double flex = 1;
}
