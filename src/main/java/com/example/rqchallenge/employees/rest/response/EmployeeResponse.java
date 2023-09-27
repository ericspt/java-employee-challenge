package com.example.rqchallenge.employees.rest.response;

public record EmployeeResponse(Long id,
                               String name,
                               Integer salary,
                               Integer age,
                               String profileImage) {
}
