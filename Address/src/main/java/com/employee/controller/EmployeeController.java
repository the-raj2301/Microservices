package com.employee.controller;

import com.employee.exceptions.MissingParameterException;
import com.employee.model.dto.EmployeeDto;
import com.employee.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable("id") Long id) {
        EmployeeDto employee = employeeService.getEmployeeById(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<EmployeeDto>> getAllEmployees() {
        Iterable<EmployeeDto> allEmployees = employeeService.getAllEmployees();
        return new ResponseEntity<>(allEmployees, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<EmployeeDto> saveEmployee(@RequestBody EmployeeDto employeeDto){
        EmployeeDto savedEmployee = employeeService.saveEmployee(employeeDto);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDto employeeDto){
        EmployeeDto updatedEmployee = employeeService.updateEmployee(id, employeeDto);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id){
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>("Employee delete successfully" ,HttpStatus.NO_CONTENT);
    }

    @GetMapping("/code-and-company")
    public ResponseEntity<EmployeeDto> getEmployeeByCodeAndCompany(@RequestParam(required = false) String empCode, @RequestParam(required = false) String companyName){
        List<String> missingParams = new ArrayList<>();
        if (empCode == null || empCode.trim().isEmpty()){
            missingParams.add("Employee code");
        }
        if (companyName == null || companyName.trim().isEmpty()){
            missingParams.add("Company Name");
        }
        if (!missingParams.isEmpty()){
            String finalMessage = String.join(", ", missingParams);
            throw new MissingParameterException("Please provide: " + finalMessage);
        }
        EmployeeDto employee = employeeService.getEmployeeByEmpCodeAndCompanyName(empCode, companyName);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }
}
