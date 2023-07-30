package com.mahrukh.timesheet.repositories;

import com.mahrukh.timesheet.entities.TimesheetTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemplateRepository extends JpaRepository<TimesheetTemplate, Long> {

}
