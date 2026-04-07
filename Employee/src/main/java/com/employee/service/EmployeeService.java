package com.employee.service;

import com.employee.model.dto.EmployeeDto;
import com.employee.repository.EmployeeRepository;

import java.util.List;

public interface EmployeeService {

    public EmployeeDto getEmployeeById(Long id);

    public List<EmployeeDto> getAllEmployees();

    public EmployeeDto saveEmployee(EmployeeDto employeeDto);

    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto);

    public void deleteEmployee(Long id);

    public EmployeeDto getEmployeeByEmpCodeAndCompanyName(String empCode, String companyName);


}
