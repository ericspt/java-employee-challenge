package com.example.rqchallenge.employees.rest;

import com.example.rqchallenge.employees.exception.NoEmployeesFoundException;
import com.example.rqchallenge.employees.rest.mapper.EmployeeMapper;
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
          try {
              return new ResponseEntity<>(mapper.getEmployeesResponseFrom(service.getAllEmployees()), HttpStatus.OK);
          } catch (Exception e) {
              return new ResponseEntity<>(HttpStatus.NOT_FOUND);
          }
    }

    @Override
    public ResponseEntity<Mono<List<EmployeeResponse>>> getEmployeesByNameSearch(String searchString) {
        try {
            return new ResponseEntity<>(
                    mapper.getEmployeesResponseFrom(service.searchEmployeesBy(searchString)),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Mono<EmployeeResponse>> getEmployeeById(String id) {
        try {
            long employeeId = Long.parseLong(id);
            return new ResponseEntity<>(mapper.getEmployeeResponseFrom(service.getEmployee(employeeId)), HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Mono<Integer>> getHighestSalaryOfEmployees() {
        try {
            return new ResponseEntity<>(service.getHighestSalaryOfEmployees(), HttpStatus.OK);
        } catch (NoEmployeesFoundException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Mono<List<String>>> getTopTenHighestEarningEmployeeNames() {
        try {
            return new ResponseEntity<>(service.getTopTenHighestEarningEmployeeNames(), HttpStatus.OK);
        } catch (NoEmployeesFoundException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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
