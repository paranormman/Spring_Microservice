package com.vestaChrono.cashingApp.services.impl;

import com.vestaChrono.cashingApp.dto.EmployeeDto;
import com.vestaChrono.cashingApp.entities.Employee;
import com.vestaChrono.cashingApp.exception.ResourceNotFoundException;
import com.vestaChrono.cashingApp.repositories.EmployeeRepository;
import com.vestaChrono.cashingApp.services.EmployeeService;
import com.vestaChrono.cashingApp.services.SalaryAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;
    private final SalaryAccountService salaryAccountService;
    private final ModelMapper modelMapper;
    private final String CACHE_NAME = "employees";

    @Override
    @Cacheable(cacheNames = CACHE_NAME, key = "#id")
    public EmployeeDto getEmployeeById(Long id) {
        log.info("Fetching employee with Id {}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Employee does not exists with id {}", id);
                    return new ResourceNotFoundException("Employee not found with id: " +id);
                });
        log.info("Successfully fetched Employee with id {}", id);
        return modelMapper.map(employee, EmployeeDto.class);
    }

    @Override
    @CachePut(cacheNames = CACHE_NAME, key = "#result.id")
    @Transactional
    public EmployeeDto createNewEmployee(EmployeeDto employeeDto) {
        log.info("Create Employee with email {}", employeeDto.getEmail());
        List<Employee> existingEmployee = employeeRepository.findByEmail(employeeDto.getEmail());
        if (!existingEmployee.isEmpty()) {
            log.error("Employee already exists with email {}", employeeDto.getEmail());
            throw new RuntimeException("Employee already exists with email" +employeeDto.getEmail());
        }

        Employee newEmployee = modelMapper.map(employeeDto, Employee.class);
        Employee savedEmployee = employeeRepository.save(newEmployee);

//        create a salary account for the employee
        salaryAccountService.createAccount(savedEmployee);

        log.info("Successfully created Employee with email {}", employeeDto.getEmail());
        return modelMapper.map(savedEmployee, EmployeeDto.class);
    }

    @Override
    @CachePut(cacheNames = CACHE_NAME, key = "#id")
    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {
        log.info("Updating employee with id: {}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Employee not found with id: {}", id);
                    return new ResourceNotFoundException("Employee not found with id: " + id);
                });

        if (!employee.getEmail().equals(employeeDto.getEmail())) {
            log.error("Attempted to update email for employee with id: {}", id);
            throw new RuntimeException("The email of the employee cannot be updated");
        }

        modelMapper.map(employeeDto, employee);
        employee.setId(id);

        Employee savedEmployee = employeeRepository.save(employee);
        log.info("Successfully updated employee with id: {}", id);
        return modelMapper.map(savedEmployee, EmployeeDto.class);
    }

    @Override
    @CacheEvict(cacheNames = CACHE_NAME, key = "#id")
    public void deleteEmployee(Long id) {
        log.info("Deleting Employee with id, {}", id);
        boolean isExists = employeeRepository.existsById(id);
        if (!isExists){
            log.error("Employee not found with id: {}", id);
            throw new RuntimeException("Employee not found with id: {}"+ id);
        }
        employeeRepository.deleteById(id);
        log.info("Successfully deleted employee with id: {}", id);
    }
}
