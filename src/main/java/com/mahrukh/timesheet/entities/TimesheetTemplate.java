package com.mahrukh.timesheet.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

import java.time.Instant;
import java.time.LocalTime;

@Entity
@Data
public class TimesheetTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String templateDay;

    @CreationTimestamp(source = SourceType.DB)
    private Instant createdOn;
    private LocalTime checkIn;
    private LocalTime checkOut;
    private LocalTime lunchBreakStart;
    private LocalTime lunchBreakEnd;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
