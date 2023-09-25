package com.example.rqchallenge.employees.employees.rest.response;

public record EmployeeResponse(Long id,
                               String name,
                               Long salary,
                               Integer age,
                               String profileImage) { }
