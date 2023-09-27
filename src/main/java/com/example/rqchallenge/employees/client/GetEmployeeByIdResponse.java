package com.example.rqchallenge.employees.client;

import com.example.rqchallenge.employees.domain.Employee;
import com.example.rqchallenge.employees.exception.EmployeeNotFoundException;

public record GetEmployeeByIdResponse(String status,
                                      GetEmployeeResponse data,
                                      String message) {
    public Employee toDomain() {
        if (data == null) {
            throw new EmployeeNotFoundException();
        }
        return data.toDomain();
    }
}
