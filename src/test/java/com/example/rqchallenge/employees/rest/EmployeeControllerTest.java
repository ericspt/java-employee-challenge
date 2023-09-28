package com.example.rqchallenge.employees.rest;

import com.example.rqchallenge.employees.domain.Employee;
import com.example.rqchallenge.employees.exception.NoEmployeesFoundException;
import com.example.rqchallenge.employees.rest.mapper.EmployeeMapper;
import com.example.rqchallenge.employees.rest.response.EmployeeResponse;
import com.example.rqchallenge.employees.service.EmployeeService;
import com.example.rqchallenge.employees.test.ControllerIntegrationTest;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest extends ControllerIntegrationTest {

    @Mock
    EmployeeMapper employeeMapper;
    @Mock
    EmployeeService employeeService;

    IEmployeeController employeeController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        employeeController = new EmployeeController(employeeMapper, employeeService);
        webTestClient = WebTestClient.bindToController(employeeController).build();
    }

    @Test
    void itFetchesAllEmployees() {
        val employeeList = Mono.just(List.of(new Employee(1L, "bob", 3, 3, ""),
                new Employee(2L, "alice", 3, 4, "")));

        val expected = List.of(new EmployeeResponse(1L, "bob", 3, 3, ""),
                new EmployeeResponse(2L, "alice", 3, 4, ""));

        Mockito.when(employeeService.getAllEmployees()).thenReturn(employeeList);
        Mockito.when(employeeMapper.getEmployeesResponseFrom(employeeList)).thenReturn(Mono.just(expected));

        webTestClient.get()
                .uri("/v1/employees")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .jsonPath("$").isArray();
    }

    @Test
    void itFetchesEmployeeByString() {
        val employeeList = Mono.just(List.of(new Employee(2L, "alice", 3, 4, "")));

        val expected = List.of(new EmployeeResponse(2L, "alice", 3, 4, ""));

        Mockito.when(employeeService.searchEmployeesBy("ce")).thenReturn(employeeList);
        Mockito.when(employeeMapper.getEmployeesResponseFrom(employeeList)).thenReturn(Mono.just(expected));

        webTestClient.get()
                .uri("/v1/search/ce")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .jsonPath("$").isArray();
    }

    @Test
    void itThrowsBadRequestIfEmployeeNotFound() {
        webTestClient.get()
                .uri("/v1/3l")
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    void itFetchesEmployeeById() {
        val employee = Mono.just(new Employee(2L, "alice", 3, 4, ""));

        val expected = new EmployeeResponse(2L, "alice", 3, 4, "");

        Mockito.when(employeeService.getEmployee(2L)).thenReturn(employee);
        Mockito.when(employeeMapper.getEmployeeResponseFrom(employee)).thenReturn(Mono.just(expected));

        webTestClient.get()
                .uri("/v1/2")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .jsonPath("$.id").isEqualTo(2);
    }

    @Test
    void itPassesNoContentForHighestSalaryWhenThereAreNoEmployees() {
        Mockito.when(employeeService.getHighestSalaryOfEmployees()).thenThrow(new NoEmployeesFoundException());

        webTestClient.get()
                .uri("/v1/highestSalary")
                .exchange()
                .expectStatus()
                .isNoContent();
    }

    @Test
    void itFetchesEmployeesHighestSalary() {
        val salary = Mono.just(123);

        val expected = 123;

        Mockito.when(employeeService.getHighestSalaryOfEmployees()).thenReturn(salary);

        webTestClient.get()
                .uri("/v1/highestSalary")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .jsonPath("$")
                .isEqualTo(expected);
    }

    @Test
    void itPassesNoContentForTop10HighestSalaryWhenThereAreNoEmployees() {
        Mockito.when(employeeService.getTopTenHighestEarningEmployeeNames()).thenThrow(new NoEmployeesFoundException());

        webTestClient.get()
                .uri("/v1/topTenHighestEarningEmployeeNames")
                .exchange()
                .expectStatus()
                .isNoContent();
    }

    @Test
    void itFetchesHighestPaidEmployees() {
        val employees = Mono.just(List.of("alice", "bob"));

        Mockito.when(employeeService.getTopTenHighestEarningEmployeeNames()).thenReturn(employees);

        webTestClient.get()
                .uri("/v1/topTenHighestEarningEmployeeNames")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .jsonPath("$").isArray();
    }

    @Test
    void itThrowsWhenCreatingEmployeeRequestInvalid() {
        val request = Map.of("name", "bob");

        webTestClient.post()
                .uri("/v1")
                .body(Mono.just(request), Map.class)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    void itCreatesAndReturnsNewEmployee() {
        final Map<String, Object> request = Map.of("name", "bob", "salary", "123", "age", "11");
        val employee = Mono.just(new Employee(1L, "bob", 123, 11, ""));
        val expected = Mono.just(new EmployeeResponse(1L, "bob", 123, 11, ""));

        Mockito.when(employeeService.createEmployee(request)).thenReturn(employee);
        Mockito.when(employeeMapper.getEmployeeResponseFrom(employee)).thenReturn(expected);

        webTestClient.post()
                .uri("/v1")
                .body(Mono.just(request), Map.class)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .jsonPath("$.id").isEqualTo(1);
    }

    @Test
    void itThrowsWhenDeletingEmployeeWithInvalidId() {
        webTestClient.delete()
                .uri("/v1/2a")
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    void itDeletesEmployee() {
        Mockito.when(employeeService.deleteEmployee(2L)).thenReturn(Mono.just("success"));

        webTestClient.delete()
                .uri("/v1/2")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .jsonPath("$").isEqualTo("success");
    }
}
