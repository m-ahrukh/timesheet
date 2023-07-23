package com.mahrukh.timesheet.services;

import com.mahrukh.timesheet.dtos.EmployeeDTO;
import com.mahrukh.timesheet.dtos.EmployeeRequest;
import com.mahrukh.timesheet.entities.Employee;
import com.mahrukh.timesheet.mappers.EmployeeMapper;
import com.mahrukh.timesheet.repositories.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    //private final EmployeeMapper employeeMapper;

    public EmployeeDTO saveEmployee(EmployeeRequest request){


        Employee employee = EmployeeMapper.INSTANCE.employeeRequestToEmployee(request);
        employeeRepository.save(employee);

        EmployeeDTO dto = EmployeeMapper.INSTANCE.employeeToEmployeeDTO(employee);

        return dto;
    }

    public List<EmployeeDTO> getAllEmployees() {
        return null;
    }
}
