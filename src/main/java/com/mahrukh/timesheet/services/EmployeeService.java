package com.mahrukh.timesheet.services;

import com.mahrukh.timesheet.dtos.EmployeeDTO;
import com.mahrukh.timesheet.dtos.EmployeeRequest;
import com.mahrukh.timesheet.entities.Employee;
import com.mahrukh.timesheet.repositories.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final ModelMapper modelMapper;
    public EmployeeDTO saveEmployee(EmployeeRequest request){


        Employee employee = modelMapper.map(request, Employee.class);
        employeeRepository.save(employee);

        return modelMapper.map(employee, EmployeeDTO.class);
    }

    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        for (Employee employee: employees) {
            employeeDTOS.add(modelMapper.map(employee, EmployeeDTO.class));
        }
        return employeeDTOS;
    }
}
