package com.musadzeyt.momentumapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

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
    private UUID activityId;
}
