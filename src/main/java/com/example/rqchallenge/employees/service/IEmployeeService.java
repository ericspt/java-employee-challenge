package com.example.rqchallenge.employees.service;

import com.example.rqchallenge.employees.domain.Employee;
import com.example.rqchallenge.employees.exception.EmployeeNotFoundException;
import com.example.rqchallenge.employees.exception.NoEmployeesFoundException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

public interface IEmployeeService {
    Mono<List<Employee>> getAllEmployees() throws NoEmployeesFoundException;
    Mono<Employee> getEmployee(long id) throws EmployeeNotFoundException;
    Mono<List<Employee>> searchEmployeesBy(String nameQuery) throws NoEmployeesFoundException;
    Mono<Integer> getHighestSalaryOfEmployees() throws NoEmployeesFoundException;
    Mono<List<String>> getTopTenHighestEarningEmployeeNames() throws NoEmployeesFoundException;
    Mono<Employee> createEmployee(Map<String, Object> employee);
    Mono<String> deleteEmployee(Long id);
}
