package com.mahrukh.timesheet.controllers;

import com.mahrukh.timesheet.dtos.EmployeeDTO;
import com.mahrukh.timesheet.dtos.EmployeeRequest;
import com.mahrukh.timesheet.dtos.TimesheetTemplateDTO;
import com.mahrukh.timesheet.dtos.TimesheetTemplateRequest;
import com.mahrukh.timesheet.entities.TimesheetTemplate;
import com.mahrukh.timesheet.services.EmployeeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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

    @GetMapping("/{username}")
    public EmployeeDTO getEmployeeByUsername(@PathVariable @Valid String username){
        return employeeService.getEmployeeByUsername(username);
    }

    @GetMapping("/id/{id}")
    public EmployeeDTO getEmployeeById(@PathVariable @Valid Long id){
        return employeeService.getEmployeeById(id);
    }

    @DeleteMapping("/{username}")
    public EmployeeDTO deleteEmployee(@PathVariable @Valid String username){
        return employeeService.deleteEmployee(username);
    }

    @DeleteMapping("/id/{id}")
    public EmployeeDTO deleteEmployeeById(@PathVariable @Valid Long id){
        return employeeService.deleteEmployeeById(id);
    }

    @PatchMapping("/id/{id}")
    public EmployeeRequest updateEmployee(@RequestBody EmployeeRequest request, @PathVariable Long id){
        return employeeService.updateEmployee(request, id);
    }

    @PostMapping("/{employeeId}/templates")
    public TimesheetTemplateDTO saveTemplate(@RequestBody @Valid TimesheetTemplateRequest request, @PathVariable Long employeeId){
        return employeeService.saveTimesheetTemplate(request, employeeId);
    }

    @GetMapping("/{employeeId}/templates")
    public List<TimesheetTemplateDTO> getTemplates(){
        return employeeService.getTemplates();
    }
}
