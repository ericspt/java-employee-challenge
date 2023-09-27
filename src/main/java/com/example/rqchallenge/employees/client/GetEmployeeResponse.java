package com.example.rqchallenge.employees.client;

import com.example.rqchallenge.employees.domain.Employee;

public record GetEmployeeResponse(Long id,
                                  String employee_name,
                                  Integer employee_salary,
                                  Integer employee_age,
                                  String profile_image) {
    public Employee toDomain() {
        return new Employee(id,
                employee_name,
                employee_salary,
                employee_age,
                profile_image);
    }
}
