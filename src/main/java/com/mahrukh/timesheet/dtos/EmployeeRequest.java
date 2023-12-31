package com.mahrukh.timesheet.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class EmployeeRequest {

    @NotEmpty
    private String firstName;
    private String lastName;

    @NotEmpty
    private String username;
    @NotEmpty
    private String password;

}
