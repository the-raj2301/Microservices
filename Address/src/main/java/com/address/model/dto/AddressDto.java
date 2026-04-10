package com.address.model.dto;

import com.address.model.enums.AddressType;
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
