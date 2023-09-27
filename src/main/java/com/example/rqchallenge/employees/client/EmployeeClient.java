package com.example.rqchallenge.employees.client;

import com.example.rqchallenge.employees.domain.Employee;
import reactor.core.publisher.Mono;

import java.util.List;

public interface EmployeeClient {
    Mono<List<Employee>> getAllEmployees();
    Mono<Employee> getEmployee(long id);
    void createEmployee(Employee employee);
    void deleteEmployee(long id);
}
