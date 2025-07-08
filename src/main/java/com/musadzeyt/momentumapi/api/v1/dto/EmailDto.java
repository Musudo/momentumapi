package com.musadzeyt.momentumapi.api.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String recipientEmail;
    private String recipientName;
    private String subject;
    private String activityName;
    private String startTime;
    private String endTime;
}
