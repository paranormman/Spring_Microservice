package com.vestaChrono.cashingApp.services.impl;

import com.vestaChrono.cashingApp.dto.EmployeeDto;
import com.vestaChrono.cashingApp.entities.Employee;
import com.vestaChrono.cashingApp.exception.ResourceNotFoundException;
import com.vestaChrono.cashingApp.repositories.EmployeeRepository;
import com.vestaChrono.cashingApp.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    @Override
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
    public EmployeeDto createNewEmployee(EmployeeDto employeeDto) {
        log.info("Create Employee with email {}", employeeDto.getEmail());
        List<Employee> existingEmployee = employeeRepository.findByEmail(employeeDto.getEmail());
        if (!existingEmployee.isEmpty()) {
            log.error("Employee already exists with email {}", employeeDto.getEmail());
            throw new RuntimeException("Employee already exists with email" +employeeDto.getEmail());
        }

        Employee newEmployee = modelMapper.map(employeeDto, Employee.class);
        Employee savedEmployee = employeeRepository.save(newEmployee);

        log.info("Successfully created Employee with email {}", employeeDto.getEmail());
        return modelMapper.map(savedEmployee, EmployeeDto.class);
    }

    @Override
    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {
        log.info("Update Employee with id: {}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.info("Employee not found with id {}", id);
                    return new ResourceNotFoundException("Employee not found with Id {}" + id);
                });

        if (!employee.getEmail().equals(employeeDto.getEmail())) {
            log.error("Attempted to update email with id: {}", id);
            throw new RuntimeException("The email of the Employee can not be updated");
        }
        modelMapper.map(employeeDto, employee);
        employee.setId(id);

        Employee savedEmployee = employeeRepository.save(employee);
        log.info("Successfully updated Employee with id {}", id);
        return modelMapper.map(savedEmployee, EmployeeDto.class);
    }

    @Override
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
