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
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final TemplateRepository templateRepository;

    private final ModelMapper modelMapper;

    public EmployeeDTO saveEmployee(EmployeeRequest request) {

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

    public EmployeeDTO getEmployeeByUsername(String username) {
        List<Employee> employees = employeeRepository.findAll();

        for (Employee employee : employees) {
            if (employee.getUsername().equals(username)) {
                return modelMapper.map(employee, EmployeeDTO.class);
            }
        }
        return null;
    }

    public EmployeeDTO getEmployeeById(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        return modelMapper.map(employee, EmployeeDTO.class);
    }

    public EmployeeDTO deleteEmployee(String username) {
        List<Employee> employees = employeeRepository.findAll();

        for (Employee emp : employees) {
            Employee employee = modelMapper.map(emp, Employee.class);
            if (emp.getUsername().equals(username)) {
                employees.remove(Math.toIntExact(employee.getId() - 1));
                return modelMapper.map(emp, EmployeeDTO.class);
            }
        }
        return null;
    }

    public EmployeeDTO deleteEmployeeById(Long id) {
        List<Employee> employees = employeeRepository.findAll();
        Optional<Employee> emp = employeeRepository.findById(id);
        Employee employee = modelMapper.map(emp, Employee.class);
        employees.remove(Math.toIntExact(employee.getId() - 1));

        for (Employee employee1 : employees) {
            return modelMapper.map(employee1, EmployeeDTO.class);
        }
        return null;
    }

    public EmployeeRequest updateEmployee(EmployeeRequest request, Long id) {
        Optional<Employee> emp = employeeRepository.findById(id);
        Employee employee = modelMapper.map(emp, Employee.class);

        if (employee != null) {
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
            return modelMapper.map(employee, EmployeeRequest.class);
        } else {
            return null;
        }
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

    public TimesheetTemplateDTO getTemplateByDay(Long employeeId, String day) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if (!optionalEmployee.isPresent()) {
            throw new EmployeeNotFound("employee not found with id " + employeeId);
        }

        Employee employee = optionalEmployee.get();

        List<TimesheetTemplate> templates = templateRepository.findAll();
        for (TimesheetTemplate template : templates) {
            if (template.getTemplateDay().equals(day)) {
                return modelMapper.map(template, TimesheetTemplateDTO.class);
            }
        }
        throw new TemplateNotFound("template not found");
    }

    public TimesheetTemplateRequest updateTemplate(TimesheetTemplateRequest request, Long employeeId, Long
            templateId) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
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
}
