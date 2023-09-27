package com.example.rqchallenge.employees.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No employees found")
public class NoEmployeesFoundException extends RuntimeException {
}
