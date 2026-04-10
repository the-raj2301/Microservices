package com.address.service;

import com.address.model.dto.AddressDto;
import com.address.model.dto.AddressRequest;

import java.util.List;

public interface AddressService {

    List<AddressDto> saveAddress(AddressRequest addressRequest);

    List<AddressDto> updateAddress(AddressRequest addressRequest);

    AddressDto getAddressById(Long id);

    List<AddressDto> getAllAddress();

    void deleteAddressById(Long id);

    List<AddressDto> getAddressByEmpId(Long empId);
}
