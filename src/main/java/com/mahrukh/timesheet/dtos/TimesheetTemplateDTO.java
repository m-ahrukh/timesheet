package com.mahrukh.timesheet.dtos;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class TimesheetTemplateDTO {
    private String day;
    private LocalDate currentDate;
    private LocalTime checkIn;
    private LocalTime checkOut;
    private LocalTime lunchBreakStart;
    private LocalTime lunchBreakEnd;
}
