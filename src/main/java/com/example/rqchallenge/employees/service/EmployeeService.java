package com.example.rqchallenge.employees.service;

import com.example.rqchallenge.employees.domain.Employee;
import com.example.rqchallenge.employees.client.EmployeeClient;
import com.example.rqchallenge.employees.exception.EmployeeNotFoundException;
import com.example.rqchallenge.employees.exception.NoEmployeesFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService implements IEmployeeService {

    private final EmployeeClient employeeClient;

    @Override
    public Mono<List<Employee>> getAllEmployees() throws NoEmployeesFoundException {
        try {
            return getNonEmptyEmployeeList(employeeClient.getAllEmployees());
        } catch (NoEmployeesFoundException e) {
            log.error("No employees found.\n" + e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public Mono<Employee> getEmployee(long id) throws EmployeeNotFoundException {
        try {
            return employeeClient.getEmployee(id);
        } catch (EmployeeNotFoundException e) {
            log.error("Employee with id " + id + " not found.\n" + e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public Mono<List<Employee>> searchEmployeesBy(String nameQuery) throws NoEmployeesFoundException {
        try {
            var allEmployees = getAllEmployees();
            return getNonEmptyEmployeeList(allEmployees.map(employeeList -> employeeList.stream()
                    .filter(employee -> employee.name().toLowerCase().contains(nameQuery.toLowerCase()))
                    .toList()));
        } catch (NoEmployeesFoundException e) {
            log.error("No employees found for search query " + nameQuery + ".\n" + e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public Mono<Integer> getHighestSalaryOfEmployees() throws NoEmployeesFoundException {
        return getHighestEarningEmployees(1)
                .map(employeeList -> employeeList.get(0).salary());
    }

    @Override
    public Mono<List<String>> getTopTenHighestEarningEmployeeNames() throws NoEmployeesFoundException {
        return getHighestEarningEmployees(10)
                .map(employeeList -> employeeList
                        .stream()
                        .map(Employee::name)
                        .toList());
    }

    private Mono<List<Employee>> getHighestEarningEmployees(int limit) throws NoEmployeesFoundException {
        var allEmployees = getAllEmployees();
        return getNonEmptyEmployeeList(allEmployees.map(employeeList -> employeeList
                .stream()
                .sorted(Comparator.comparingInt(Employee::salary)
                        .reversed())
                .limit(limit)
                .toList()));
    }

    private Mono<List<Employee>> getNonEmptyEmployeeList(Mono<List<Employee>> employeeList) throws NoEmployeesFoundException {
        return employeeList.map(employees -> {
            if (employees.isEmpty())
                throw new NoEmployeesFoundException();
            return employees;
        });
    }
}
