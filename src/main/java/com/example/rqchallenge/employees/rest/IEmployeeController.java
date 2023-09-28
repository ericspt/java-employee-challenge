package com.example.rqchallenge.employees.rest;

import com.example.rqchallenge.employees.rest.response.EmployeeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RequestMapping("/v1")
public interface IEmployeeController {

    @GetMapping("/employees")
    ResponseEntity<Mono<List<EmployeeResponse>>> getAllEmployees();

    @GetMapping("/search/{searchString}")
    ResponseEntity<Mono<List<EmployeeResponse>>> getEmployeesByNameSearch(@PathVariable String searchString);

    @GetMapping("/{id}")
    ResponseEntity<Mono<EmployeeResponse>> getEmployeeById(@PathVariable String id);

    @GetMapping("/highestSalary")
    ResponseEntity<Mono<Integer>> getHighestSalaryOfEmployees();

    @GetMapping("/topTenHighestEarningEmployeeNames")
    ResponseEntity<Mono<List<String>>> getTopTenHighestEarningEmployeeNames();

    @PostMapping()
    ResponseEntity<Mono<EmployeeResponse>> createEmployee(@RequestBody Map<String, Object> employeeInput);

    @DeleteMapping("/{id}")
    ResponseEntity<Mono<String>> deleteEmployeeById(@PathVariable String id);

}
