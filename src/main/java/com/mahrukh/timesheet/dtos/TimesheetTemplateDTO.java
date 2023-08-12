package com.mahrukh.timesheet.dtos;

import lombok.Data;

import java.time.Instant;
import java.time.LocalTime;

@Data
public class TimesheetTemplateDTO {
    private Long id;
    private Instant createdOn;
    private String templateDay;
    private LocalTime checkIn;
    private LocalTime checkOut;
    private LocalTime lunchBreakStart;
    private LocalTime lunchBreakEnd;
}
