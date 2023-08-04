package com.mahrukh.timesheet.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalTime;

@Data
public class TimesheetTemplateRequest {
    private String templateDay;

    @JsonFormat(pattern="HH:mm:ss")
    private LocalTime checkIn;

    @JsonFormat(pattern="HH:mm:ss")
    private LocalTime checkOut;

    @JsonFormat(pattern="HH:mm:ss")
    private LocalTime lunchBreakStart;

    @JsonFormat(pattern="HH:mm:ss")
    private LocalTime lunchBreakEnd;
}
