package com.example.rqchallenge.employees.service;

import com.example.rqchallenge.employees.client.EmployeeClient;
import com.example.rqchallenge.employees.domain.Employee;
import com.example.rqchallenge.employees.exception.EmployeeNotFoundException;
import com.example.rqchallenge.employees.exception.NoEmployeesFoundException;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    EmployeeClient employeeClient;

    @InjectMocks
    EmployeeService employeeService;

    @Test
    void itGetsAllEmployees() {
        val expected = Mono.just(List.of(new Employee(1L, "bob", 3, 3, ""),
                new Employee(2L, "alice", 3, 4, "")));

        Mockito.when(employeeClient.getAllEmployees()).thenReturn(
                Mono.just(List.of(new Employee(1L, "bob", 3, 3, ""),
                        new Employee(2L, "alice", 3, 4, ""))));

        val result = employeeService.getAllEmployees();

        Assertions.assertEquals(expected.block(), result.block());
    }

    @Test
    void itThrowsWhenItGetsAllEmployees() {
        Mockito.when(employeeClient.getAllEmployees()).thenThrow(NoEmployeesFoundException.class);

        Assertions.assertThrows(NoEmployeesFoundException.class, () -> employeeService.getAllEmployees());
    }

    @Test
    void itGetsEmployeeById() {
        val expected = Mono.just(new Employee(1L, "bob", 3, 3, ""));

        Mockito.when(employeeClient.getEmployee(1L)).thenReturn(
                Mono.just(new Employee(1L, "bob", 3, 3, "")));

        val result = employeeService.getEmployee(1L);

        Assertions.assertEquals(expected.block(), result.block());
    }

    @Test
    void itThrowsWhenItCannotGetEmployee() {
        Mockito.when(employeeClient.getEmployee(2L)).thenThrow(EmployeeNotFoundException.class);

        Assertions.assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployee(2L));
    }

    @Test
    void itGetsEmployeesBySearchQuery() {
        val expected = Mono.just(List.of(new Employee(1L, "bob", 3, 3, "")));

        Mockito.when(employeeClient.getAllEmployees()).thenReturn(
                Mono.just(List.of(new Employee(1L, "bob", 3, 3, ""),
                        new Employee(2L, "alice", 3, 4, ""))));

        val result = employeeService.searchEmployeesBy("bo");

        Assertions.assertEquals(expected.block(), result.block());
    }

    @Test
    void itThrowsWhenItGetsEmployeesBySearchQuery() {
        Mockito.when(employeeClient.getAllEmployees()).thenReturn(
                Mono.just(List.of(new Employee(1L, "bob", 3, 3, ""),
                        new Employee(2L, "alice", 3, 4, ""))));

        Assertions.assertThrows(NoEmployeesFoundException.class,
                () -> employeeService.searchEmployeesBy("se").block());
    }

    @Test
    void itGetsHighestEarningEmployeesSalary() {
        Mockito.when(employeeClient.getAllEmployees()).thenReturn(
                Mono.just(List.of(new Employee(1L, "bob", 3, 3, ""),
                        new Employee(2L, "alice", 4, 4, ""))));

        val result = employeeService.getHighestSalaryOfEmployees();

        Assertions.assertEquals(result.block(), 4);
    }

    @Test
    void itThrowsWhenItGetsHighestEarningEmployeesSalaryIfThereAreNoEmployees() {
        Mockito.when(employeeClient.getAllEmployees()).thenReturn(Mono.just(List.of()));

        Assertions.assertThrows(NoEmployeesFoundException.class,
                () -> employeeService.getHighestSalaryOfEmployees().block());
    }

    @Test
    void itThrowsWhenHighestEarningEmployeesIsCalledWithNoEmployees() {
        Mockito.when(employeeClient.getAllEmployees()).thenReturn(
                Mono.just(List.of()));

        Assertions.assertThrows(NoEmployeesFoundException.class,
                () -> employeeService.getTopTenHighestEarningEmployeeNames().block());
    }

    @Test
    void itGetsHighestEarningEmployeesNamesWhenThereAreNotEnoughEmployees() {
        Mockito.when(employeeClient.getAllEmployees()).thenReturn(
                Mono.just(List.of(new Employee(1L, "bob", 3, 3, ""),
                        new Employee(2L, "alice", 4, 4, ""))));

        val result = employeeService.getTopTenHighestEarningEmployeeNames();
        val expected = List.of("alice", "bob");

        Assertions.assertEquals(result.block(), expected);
    }

    @Test
    void itGetsHighestEarningEmployeesNames() {
        Mockito.when(employeeClient.getAllEmployees()).thenReturn(
                Mono.just(List.of(
                        new Employee(1L, "bob3", 3, 3, ""),
                        new Employee(2L, "bob1", 1, 3, ""),
                        new Employee(3L, "bob5", 5, 3, ""),
                        new Employee(4L, "bob4", 4, 3, ""),
                        new Employee(5L, "bob2", 2, 3, ""),
                        new Employee(6L, "bob7", 7, 3, ""),
                        new Employee(7L, "bob6", 6, 3, ""),
                        new Employee(8L, "bob9", 9, 3, ""),
                        new Employee(9L, "bob10", 10, 3, ""),
                        new Employee(10L, "bob8", 8, 3, "")
                )));

        val result = employeeService.getTopTenHighestEarningEmployeeNames();
        val expected = List.of("bob10", "bob9", "bob8", "bob7", "bob6", "bob5", "bob4", "bob3", "bob2", "bob1");

        Assertions.assertEquals(result.block(), expected);
    }

    @Test
    void itCreatesNewEmployee() {
        Map<String, Object> employeeData = Map.of("name", "bob", "salary", "123", "age", "11");

        Mockito.when(employeeClient.createEmployee(employeeData))
                .thenReturn(Mono.just(new Employee(1L, "bob", 123, 11, "")));

        val result = employeeService.createEmployee(employeeData);
        val expected = new Employee(1L, "bob", 123, 11, "");

        Assertions.assertEquals(result.block(), expected);
    }

    @Test
    void itDeletesEmployee() {
        Mockito.when(employeeClient.deleteEmployee(3L)).thenReturn(Mono.just("success"));

        val result = employeeService.deleteEmployee(3L);
        val expected = "success";

        Assertions.assertEquals(result.block(), expected);
    }
}
