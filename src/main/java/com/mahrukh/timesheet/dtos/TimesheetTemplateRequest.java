package com.mahrukh.timesheet.dtos;

import lombok.Data;

import java.time.LocalTime;

@Data
public class TimesheetTemplateRequest {
    private String templateDay;
    private LocalTime checkIn;
    private LocalTime checkOut;
    private LocalTime lunchBreakStart;
    private LocalTime lunchBreakEnd;
}
