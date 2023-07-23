package com.mahrukh.timesheet.controllers;

import com.mahrukh.timesheet.dtos.EmployeeDTO;
import com.mahrukh.timesheet.dtos.EmployeeRequest;
import com.mahrukh.timesheet.services.EmployeeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("employees")
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public EmployeeDTO saveEmployee(@RequestBody @Valid EmployeeRequest employee){
        return employeeService.saveEmployee(employee);
    }

    @GetMapping
    public List<EmployeeDTO> getAllEmployees(){
        return employeeService.getAllEmployees();
    }



}
