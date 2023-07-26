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
import java.util.Optional;

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

    public EmployeeDTO getEmployeeByUsername(String username) {
        List<Employee> employees = employeeRepository.findAll();

        for (Employee employee: employees) {
            if(employee.getUsername().equals(username)){
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

        for (Employee emp: employees) {
            Employee employee = modelMapper.map(emp, Employee.class);
            if(emp.getUsername().equals(username)){
                employees.remove(Math.toIntExact(employee.getId()-1));
                return modelMapper.map(emp, EmployeeDTO.class);
            }
        }
        return null;
    }

    public EmployeeDTO deleteEmployeeById(Long id) {
        List<Employee> employees = employeeRepository.findAll();
        Optional<Employee> emp = employeeRepository.findById(id);
        Employee employee = modelMapper.map(emp, Employee.class);
        employees.remove(Math.toIntExact(employee.getId()-1));

        for(Employee employee1 : employees){
            return modelMapper.map(employee1, EmployeeDTO.class);
        }
        return null;
    }

    public EmployeeRequest updateEmployee(EmployeeRequest request, Long id) {
        Optional<Employee> emp = employeeRepository.findById(id);
        Employee employee = modelMapper.map(emp, Employee.class);

        if(employee != null){
            if(request.getFirstName() != null){
                employee.setFirstName(request.getFirstName());
            }
            if(request.getLastName() != null){
                employee.setLastName(request.getLastName());
            }
            if(request.getPassword() != null){
                employee.setPassword(request.getPassword());
            }
            if(request.getUsername() != null){
                employee.setUsername(request.getUsername());
            }
            employeeRepository.saveAndFlush(employee);
            return modelMapper.map(employee, EmployeeRequest.class);
        }
        else{
            return null;
        }
    }
}
