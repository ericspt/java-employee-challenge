package com.example.rqchallenge.employees.rest;

import com.example.rqchallenge.employees.exception.NoEmployeesFoundException;
import com.example.rqchallenge.employees.rest.mapper.EmployeeMapper;
import com.example.rqchallenge.employees.rest.request.CreateEmployeeRequest;
import com.example.rqchallenge.employees.rest.response.EmployeeResponse;
import com.example.rqchallenge.employees.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@RestController
public class EmployeeController implements IEmployeeController {

    private final EmployeeMapper mapper;
    private final EmployeeService service;

    @Override
    public ResponseEntity<Mono<List<EmployeeResponse>>> getAllEmployees() {
          var employees = service.getAllEmployees();
          var response = mapper.getEmployeesResponseFrom(employees);
          return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Mono<List<EmployeeResponse>>> getEmployeesByNameSearch(String searchString) {
        var employees = service.searchEmployeesBy(searchString);
        var response = mapper.getEmployeesResponseFrom(employees);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Mono<EmployeeResponse>> getEmployeeById(String id) {
        try {
            Long.parseLong(id);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        var employee = service.getEmployee(Long.parseLong(id));
        var response = mapper.getEmployeeResponseFrom(employee);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Mono<Integer>> getHighestSalaryOfEmployees() {
        try {
            var response = service.getHighestSalaryOfEmployees();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NoEmployeesFoundException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @Override
    public ResponseEntity<Mono<List<String>>> getTopTenHighestEarningEmployeeNames() {
        try {
            var response = service.getTopTenHighestEarningEmployeeNames();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NoEmployeesFoundException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @Override
    public ResponseEntity<Mono<EmployeeResponse>> createEmployee(Map<String, Object> employeeInput) {
        try {
            CreateEmployeeRequest request = new CreateEmployeeRequest(employeeInput);
            if (!request.isValid()) {
                throw new RuntimeException();
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        var employee = service.createEmployee(employeeInput);
        var response = mapper.getEmployeeResponseFrom(employee);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Mono<String>> deleteEmployeeById(String id) {
        try {
            Long.parseLong(id);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        var response = service.deleteEmployee(Long.parseLong(id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
