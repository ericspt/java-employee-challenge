package com.example.rqchallenge.employees.employees.rest;

import com.example.rqchallenge.employees.employees.rest.response.EmployeeResponse;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class EmployeeController implements IEmployeeController {
    @Override
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees() throws IOException {
        return null;
    }

    @Override
    public ResponseEntity<List<EmployeeResponse>> getEmployeesByNameSearch(String searchString) {
        return null;
    }

    @Override
    public ResponseEntity<EmployeeResponse> getEmployeeById(String id) {
        return null;
    }

    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        return null;
    }

    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        return null;
    }

    @Override
    public ResponseEntity<EmployeeResponse> createEmployee(Map<String, Object> employeeInput) {
        return null;
    }

    @Override
    public ResponseEntity<String> deleteEmployeeById(String id) {
        return null;
    }
}
