package com.example.rqchallenge.employees.client;

import com.example.rqchallenge.employees.domain.Employee;
import com.example.rqchallenge.employees.exception.NoEmployeesFoundException;

import java.util.List;

public record GetEmployeesResponse(String status,
                                   List<GetEmployeeResponse> data,
                                   String message) {
    public List<Employee> toDomain() {
        if (!status.equals("success") || data == null || data.isEmpty()) {
            throw new NoEmployeesFoundException();
        }
        return data.stream().map(GetEmployeeResponse::toDomain).toList();
    }
}
