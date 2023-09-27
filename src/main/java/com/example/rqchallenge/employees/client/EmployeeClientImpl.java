package com.example.rqchallenge.employees.client;

import com.example.rqchallenge.employees.domain.Employee;
import com.example.rqchallenge.employees.exception.EmployeeNotFoundException;
import com.example.rqchallenge.employees.exception.NoEmployeesFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@AllArgsConstructor
@Validated
@Slf4j
public class EmployeeClientImpl implements EmployeeClient {
    @Qualifier("employeeWebClient")
    private final WebClient webClient;

    private static final String GET_ALL_EMPLOYEES_URL = "/employees";
    private static final String GET_EMPLOYEE_URL = "/employee/{id}";

    @Override
    public Mono<List<Employee>> getAllEmployees() throws NoEmployeesFoundException {
        return webClient.get()
                .uri(GET_ALL_EMPLOYEES_URL)
                .retrieve()
                .bodyToMono(GetEmployeesResponse.class)
                .map(GetEmployeesResponse::toDomain);
    }

    @Override
    public Mono<Employee> getEmployee(long id) throws EmployeeNotFoundException {
        return webClient.get()
                .uri(GET_EMPLOYEE_URL, id)
                .retrieve()
                .bodyToMono(GetEmployeeByIdResponse.class)
                .map(GetEmployeeByIdResponse::toDomain);
    }

    @Override
    public void createEmployee(Employee employee) {

    }

    @Override
    public void deleteEmployee(long id) {

    }
}
