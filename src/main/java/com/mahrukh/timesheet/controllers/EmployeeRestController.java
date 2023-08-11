package com.mahrukh.timesheet.controllers;

import com.mahrukh.timesheet.dtos.*;
import com.mahrukh.timesheet.exceptions.EmployeeNotFound;
import com.mahrukh.timesheet.services.EmployeeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("employees")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class EmployeeRestController {

    private final EmployeeService employeeService;

    @PostMapping
    public EmployeeDTO saveEmployee(@RequestBody @Valid EmployeeRequest employee){
        return employeeService.saveEmployee(employee);
    }

    @GetMapping
    public List<EmployeeDTO> getAllEmployees(){
        return employeeService.getAllEmployees();
    }


    @GetMapping("/{id}")
    public EmployeeDTO getEmployeeById(@PathVariable @Valid Long id){
        return employeeService.getEmployeeById(id);
    }

//    @DeleteMapping("/{username}")
//    public EmployeeDTO deleteEmployee(@PathVariable @Valid String username){
//        return employeeService.deleteEmployee(username);
//    }

    @DeleteMapping("/{id}")
    public EmployeeDTO deleteEmployeeById(@PathVariable @Valid Long id){
        return employeeService.deleteEmployeeById(id);
    }

    @PatchMapping("/{id}")
    public EmployeeRequest updateEmployee(@RequestBody EmployeeRequest request, @PathVariable Long id){
        return employeeService.updateEmployee(request, id);
    }

    @PostMapping("/{employeeId}/templates")
    public TimesheetTemplateDTO saveTemplate(@RequestBody @Valid TimesheetTemplateRequest request, @PathVariable Long employeeId){
        return employeeService.saveTimesheetTemplate(request, employeeId);
    }

    @GetMapping("/{employeeId}/templates")
    public List<TimesheetTemplateDTO> getTemplates(@PathVariable Long employeeId){
        return employeeService.getTemplates(employeeId);
    }

    @GetMapping("/{employeeId}/templates/{templateDay}")
    public TimesheetTemplateDTO getTemplateByDay(@PathVariable Long employeeId, @PathVariable String templateDay){
        return employeeService.getTemplateByDay(employeeId, templateDay);
    }

    @PatchMapping("/{employeeId}/templates/{templateId}")
    public TimesheetTemplateRequest updateTemplate(@RequestBody TimesheetTemplateRequest request, @PathVariable Long employeeId, @PathVariable Long templateId){
        return employeeService.updateTemplate(request, employeeId, templateId);
    }


    @ExceptionHandler(EmployeeNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleItemNotFoundException(EmployeeNotFound exception,
            WebRequest request
    ){
        log.info("Failed to find the requested element", exception);
        return buildErrorResponse(exception, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(Exception exception, HttpStatus httpStatus) {
        ErrorResponse errorResponse = new ErrorResponse(
                httpStatus.value(),
                exception.getMessage()
        );

        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleInternalException(EmployeeNotFound exception,
                                                                     WebRequest request
    ){
        log.error("Failed to find the requested element", exception);
        return buildErrorResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}