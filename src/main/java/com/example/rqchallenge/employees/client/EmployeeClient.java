package com.example.rqchallenge.employees.client;

import com.example.rqchallenge.employees.domain.Employee;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

public interface EmployeeClient {
    Mono<List<Employee>> getAllEmployees();
    Mono<Employee> getEmployee(long id);
    Mono<Employee> createEmployee(Map<String, Object> employeeData);
    Mono<String> deleteEmployee(long id);
}
