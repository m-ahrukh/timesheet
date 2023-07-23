package com.mahrukh.timesheet.repositories;

import com.mahrukh.timesheet.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
