package com.musadzeyt.momentumapi.dto.stat;

import com.musadzeyt.momentumapi.enums.stat.ColumnNameEnum;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class TableColumn {
    private String field;
    private ColumnNameEnum headerName;
    private int minWidth;
    @Builder.Default
    private String headerAlign = "right";
    @Builder.Default
    private String align = "right";
    @Builder.Default
    private double flex = 1;
}
