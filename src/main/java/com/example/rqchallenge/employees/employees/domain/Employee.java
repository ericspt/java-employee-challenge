package com.example.rqchallenge.employees.employees.domain;

public record Employee(Long id,
                       String name,
                       Long salary,
                       Integer age,
                       String profileImage) { }
