package com.example.rqchallenge.employees.domain;

public record Employee(Long id,
                       String name,
                       Integer salary,
                       Integer age,
                       String profileImage) { }
