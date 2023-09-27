package com.example.rqchallenge.employees.rest.mapper;

import com.example.rqchallenge.employees.domain.Employee;
import com.example.rqchallenge.employees.rest.response.EmployeeResponse;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.util.List;

@Configuration
public class EmployeeMapper {
    public Mono<List<EmployeeResponse>> getEmployeesResponseFrom(Mono<List<Employee>> employeeList) {
        return employeeList.map(empList -> empList.stream().map(this::getEmployeeResponseFrom).toList());
    }

    public Mono<EmployeeResponse> getEmployeeResponseFrom(Mono<Employee> employee) {
        return employee.map(e -> new EmployeeResponse(e.id(),
                e.name(),
                e.salary(),
                e.age(),
                e.profileImage()));
    }

    private EmployeeResponse getEmployeeResponseFrom(Employee employee) {
        return new EmployeeResponse(employee.id(),
                employee.name(),
                employee.salary(),
                employee.age(),
                employee.profileImage());
    }
}
