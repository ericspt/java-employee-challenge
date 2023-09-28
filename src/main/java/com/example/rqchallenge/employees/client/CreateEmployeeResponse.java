package com.example.rqchallenge.employees.client;

import com.example.rqchallenge.employees.domain.Employee;

public record CreateEmployeeResponse(String success, CreateEmployeeDataResponse data) {
    public Employee toDomain() {
        return new Employee(data.id(),
                data.name(),
                Integer.parseInt(data.salary()),
                Integer.parseInt(data.age()),
                "");
    }
}
