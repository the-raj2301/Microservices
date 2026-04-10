package com.employee.service.impl;

import com.employee.client.AddressClient;
import com.employee.exceptions.BadRequestException;
import com.employee.exceptions.ResourceNotFoundException;
import com.employee.model.dto.AddressDto;
import com.employee.model.dto.EmployeeDto;
import com.employee.model.entity.Employee;
import com.employee.repository.EmployeeRepository;
import com.employee.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    private final AddressClient addressClient;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper modelMapper, AddressClient addressClient) {
        this.modelMapper = modelMapper;
        this.employeeRepository = employeeRepository;
        this.addressClient = addressClient;
    }

    @Override
    public EmployeeDto getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id ));

        List<AddressDto> addresses = fetchAddressByEmpId(id);
        EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);
        employeeDto.setAddress(addresses);
        return employeeDto;
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        if (employees.isEmpty()){
            throw new ResourceNotFoundException("Employee not found");
        }
        return employees.stream().map(employee -> {
            List<AddressDto> addresses = fetchAddressByEmpId(employee.getId());
            EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);
            employeeDto.setAddress(addresses);
            return employeeDto;
        }).toList();
    }

    private List<AddressDto> fetchAddressByEmpId(Long empId) {
        try {
            List<AddressDto> addresses = addressClient.getAddressByEmpId(empId);
            log.info("Fetched {} address(es) for employee: {}", addresses.size(), empId);
            return addresses;
        }catch (Exception e){
            log.warn("Address service unavailable for empId: {}. Returning empty list.", empId);
            return Collections.emptyList();
        }
    }

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        if (employeeDto.getId() != null){
            throw new BadRequestException("Employee id is not expected");
        }
        Employee employee = modelMapper.map(employeeDto, Employee.class);
        Employee saved = employeeRepository.save(employee);
        return modelMapper.map(saved, EmployeeDto.class);
    }

    @Override
    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {
        if (id == null || employeeDto.getId() == null){
            throw new BadRequestException("Please provide employee's id");
        }

        if(!Objects.equals(id, employeeDto.getId())){
            throw new BadRequestException("Employee id mismatch");
        }

        employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id ));

        Employee updateEmp = employeeRepository.save(modelMapper.map(employeeDto, Employee.class));
        return modelMapper.map(updateEmp, EmployeeDto.class);
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id ));
        employeeRepository.delete(employee);
    }

    @Override
    public EmployeeDto getEmployeeByEmpCodeAndCompanyName(String empCode, String companyName) {
        Employee employee = employeeRepository.findByEmpCodeAndCompanyName(empCode, companyName).orElseThrow(() -> new ResourceNotFoundException("Employee Not found with Employee Code: " + empCode + " And Company Name: " + companyName));
        return modelMapper.map(employee, EmployeeDto.class);
    }
}
