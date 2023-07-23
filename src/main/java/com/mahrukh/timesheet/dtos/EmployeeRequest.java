package com.mahrukh.timesheet.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class EmployeeRequest {

    @NotEmpty
    private String firstName;
    private String lastName;

    @NotEmpty
    private String username;
    private String password;

}
