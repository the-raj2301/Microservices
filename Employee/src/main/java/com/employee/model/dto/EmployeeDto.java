package com.employee.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class EmployeeDto {
    private Long id;
    private String empName;
    private String empEmail;
    private String empCode;
    private String companyName;

    private List<AddressDto> address;
}
