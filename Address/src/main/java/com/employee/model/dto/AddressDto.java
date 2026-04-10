package com.employee.model.dto;

import com.employee.model.enums.AddressType;
import lombok.Data;

@Data
public class AddressDto {
    private Long id;

    private Long empId;
    private String street;
    private Long pinCode;
    private String city;
    private String country;

    private AddressType addressType;
}
