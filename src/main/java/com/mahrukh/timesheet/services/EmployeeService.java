package com.mahrukh.timesheet.services;

import com.mahrukh.timesheet.dtos.EmployeeDTO;
import com.mahrukh.timesheet.dtos.EmployeeRequest;
import com.mahrukh.timesheet.dtos.TimesheetTemplateDTO;
import com.mahrukh.timesheet.dtos.TimesheetTemplateRequest;
import com.mahrukh.timesheet.entities.Employee;
import com.mahrukh.timesheet.entities.TimesheetTemplate;
import com.mahrukh.timesheet.exceptions.EmployeeNotFound;
import com.mahrukh.timesheet.exceptions.TemplateNotFound;
import com.mahrukh.timesheet.repositories.EmployeeRepository;
import com.mahrukh.timesheet.repositories.TemplateRepository;
import com.sun.tools.jconsole.JConsoleContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.sql.Template;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final TemplateRepository templateRepository;

    private final ModelMapper modelMapper;


    public EmployeeDTO saveEmployee(EmployeeRequest request) {

        log.info("saving user with username: {}", request.getUsername());

        Employee employee = modelMapper.map(request, Employee.class);
        employeeRepository.save(employee);
        return modelMapper.map(employee, EmployeeDTO.class);
    }

    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();

        for (Employee employee : employees) {
            employeeDTOS.add(modelMapper.map(employee, EmployeeDTO.class));
        }

        return employeeDTOS;
    }


    public EmployeeDTO getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .map(employee -> modelMapper.map(employee, EmployeeDTO.class))
                .orElseThrow(() -> new EmployeeNotFound("employee not found with id: " + id));
    }

    public EmployeeDTO deleteEmployeeById(Long id) {
        Employee employee1 = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFound("employee not found with id: " + id));

        employeeRepository.delete(employee1);

        return modelMapper.map(employee1, EmployeeDTO.class);

    }

    public EmployeeRequest updateEmployee(EmployeeRequest request, Long userId) {

        log.info("updating user with userId : {}", userId);

        Optional<Employee> optionalEmployee = employeeRepository.findById(userId);
        if (!optionalEmployee.isPresent()) {
            throw new EmployeeNotFound("employee not found with userId " + userId);
        }

        Employee employee = optionalEmployee.get();

        if (request.getFirstName() != null) {
            employee.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            employee.setLastName(request.getLastName());
        }
        if (request.getPassword() != null) {
            employee.setPassword(request.getPassword());
        }
        if (request.getUsername() != null) {
            employee.setUsername(request.getUsername());
        }
        employeeRepository.saveAndFlush(employee);

        log.info("updated user with id: {}", userId);

        return modelMapper.map(employee, EmployeeRequest.class);
    }

    public TimesheetTemplateDTO saveTimesheetTemplate(TimesheetTemplateRequest request, Long employeeId) {

        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if (!optionalEmployee.isPresent()) {
            throw new EmployeeNotFound("employee not found with id " + employeeId);
        }

        Employee employee = optionalEmployee.get();

        TimesheetTemplate template = modelMapper.map(request, TimesheetTemplate.class);

        Employee employee1 = new Employee();
        employee1.setId(employeeId);
        template.setEmployee(employee1);
        templateRepository.save(template);
        return modelMapper.map(template, TimesheetTemplateDTO.class);
    }


    public List<TimesheetTemplateDTO> getTemplates(Long employeeId) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);

        if (!optionalEmployee.isPresent()) {
            throw new EmployeeNotFound("employee not found with id " + employeeId);
        }

        Employee employee = optionalEmployee.get();


        List<TimesheetTemplate> templates = templateRepository.findAll();
        List<TimesheetTemplateDTO> templateDTOS = new ArrayList<>();

        for (TimesheetTemplate template : templates) {
            if (template.getEmployee().getId().equals(employeeId)) {
                templateDTOS.add(modelMapper.map(template, TimesheetTemplateDTO.class));
            }
        }

        return templateDTOS;
    }

    public TimesheetTemplateDTO getTemplateById(Long employeeId, Long templateId) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if (!optionalEmployee.isPresent()) {
            throw new EmployeeNotFound("employee not found with id " + employeeId);
        }

        Employee employee = optionalEmployee.get();

        List<TimesheetTemplate> templates = templateRepository.findAll();
        for (TimesheetTemplate template : templates) {
            if (template.getId().equals(templateId)) {
                return modelMapper.map(template, TimesheetTemplateDTO.class);
            }
        }
        throw new TemplateNotFound("template not found");
    }

    public TimesheetTemplateRequest updateTemplate(TimesheetTemplateRequest request, Long employeeId, Long
            templateId) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);

        System.out.println("op emp "+ optionalEmployee);
        if (!optionalEmployee.isPresent()) {
            throw new EmployeeNotFound("employee not found with id " + employeeId);
        }

        Employee employee = optionalEmployee.get();

        System.out.println("employee " + employee);

        Optional<TimesheetTemplate> template = templateRepository.findById(templateId);

        if (!template.isPresent()) {
            throw new TemplateNotFound("template not found with id " + templateId);
        }

        TimesheetTemplate timesheetTemplate = template.get();

        System.out.println("timesheet template data " + timesheetTemplate);

        if (request.getTemplateDay() != null) {
            timesheetTemplate.setTemplateDay(request.getTemplateDay());
        }
        if (request.getCheckIn() != null) {
            timesheetTemplate.setCheckIn(request.getCheckIn());
        }
        if (request.getCheckOut() != null) {
            timesheetTemplate.setCheckOut(request.getCheckOut());
        }
        if (request.getLunchBreakStart() != null) {
            timesheetTemplate.setLunchBreakStart(request.getLunchBreakStart());
        }
        if (request.getLunchBreakEnd() != null) {
            timesheetTemplate.setLunchBreakEnd(request.getLunchBreakEnd());
        }
        templateRepository.saveAndFlush(timesheetTemplate);
        return modelMapper.map(timesheetTemplate, TimesheetTemplateRequest.class);
    }


    public TimesheetTemplateDTO deleteTemplateById(Long employeeId, Long templateId) {
        Optional<TimesheetTemplate> template = templateRepository.findById(templateId);
        if (!template.isPresent()) {
            throw new TemplateNotFound("template not found with id " + templateId);
        }
        templateRepository.deleteById(templateId);
        return modelMapper.map(template, TimesheetTemplateDTO.class);
    }
}
