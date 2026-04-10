package com.address.service.impl;

import com.address.client.EmployeeClient;
import com.address.exceptions.ResourceNotFoundException;
import com.address.model.dto.AddressDto;
import com.address.model.dto.AddressRequest;
import com.address.model.dto.AddressRequestDto;
import com.address.model.dto.EmployeeDto;
import com.address.model.entity.Address;
import com.address.repository.AddressRepository;
import com.address.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;
    private final EmployeeClient employeeClient;

    public AddressServiceImpl(AddressRepository addressRepository, ModelMapper modelMapper, EmployeeClient employeeClient) {
        this.modelMapper = modelMapper;
        this.addressRepository = addressRepository;
        this.employeeClient = employeeClient;
    }

    @Override
    public List<AddressDto> saveAddress(AddressRequest addressRequest) {
        // TODO: Ckeck if employee exist with requested Id;
        EmployeeDto employee = employeeClient.getEmployeeById(addressRequest.getEmpId());
//        if (employee == null) {
//            throw new ResourceNotFoundException("Employee not found with id: " + addressRequest.getEmpId());
//        }

        List<Address> listToSave = new ArrayList<>();
        for (AddressRequestDto addressRequestDto: addressRequest.getAddresses()){
            Address address = modelMapper.map(addressRequestDto, Address.class);
            address.setEmpId(addressRequest.getEmpId());
            listToSave.add(address);
        }

        List<Address> savedAddresses = addressRepository.saveAll(listToSave);
        log.info("Saved addresses: {}", savedAddresses);
        return savedAddresses.stream().map(address -> modelMapper.map(address, AddressDto.class)).toList();
    }

    @Override
    public List<AddressDto> updateAddress(AddressRequest addressRequest) {
        // TODO: Ckeck if employee exist with requested Id;
        EmployeeDto employee = employeeClient.getEmployeeById(addressRequest.getEmpId());

        List<AddressDto> updatedAddresses = new ArrayList<>();
        for (AddressRequestDto addressRequestDto: addressRequest.getAddresses()){
            Address savedAddress;
            if (addressRequestDto.getId() != null){
                Address addressFromDB = addressRepository.findById(addressRequestDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + addressRequestDto.getId()));

                addressFromDB.setStreet(addressRequestDto.getStreet());
                addressFromDB.setPinCode(addressRequestDto.getPinCode());
                addressFromDB.setCity(addressRequestDto.getCity());
                addressFromDB.setCountry(addressRequestDto.getCountry());
                addressFromDB.setAddressType(addressRequestDto.getAddressType());

                savedAddress = addressRepository.save(addressFromDB);

            }else {
                Address newAddress = modelMapper.map(addressRequestDto, Address.class);
                newAddress.setEmpId(addressRequest.getEmpId());
                savedAddress = addressRepository.save(newAddress);
            }
            log.info("Updating address: {}", savedAddress);
            updatedAddresses.add(modelMapper.map(savedAddress, AddressDto.class));
        }
        return updatedAddresses;
    }

    @Override
    public AddressDto getAddressById(Long id) {
        Address address = addressRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + id));
        return modelMapper.map(address, AddressDto.class);
    }

    @Override
    public List<AddressDto> getAllAddress() {
        List<Address> addresses = addressRepository.findAll();
        if (addresses.isEmpty()){
            throw new ResourceNotFoundException("Address not found");
        }
        return addresses.stream().map(address -> modelMapper.map(address, AddressDto.class)).toList();
    }

    @Override
    public void deleteAddressById(Long id) {
        Address address = addressRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + id));
        addressRepository.delete(address);
        log.info("Deleted address: {}", id);
    }

    @Override
    public List<AddressDto> getAddressByEmpId(Long empId) {

        List<Address> allAddressesByEmpId = addressRepository.findAllByEmpId(empId);
        log.info("Address are: {}", allAddressesByEmpId);
        return allAddressesByEmpId.stream().map(address -> modelMapper.map(address, AddressDto.class)).toList();
    }


}
