package com.example.rqchallenge.employees.rest;

import com.example.rqchallenge.employees.domain.Employee;
import com.example.rqchallenge.employees.rest.mapper.EmployeeMapper;
import com.example.rqchallenge.employees.rest.response.EmployeeResponse;
import com.example.rqchallenge.employees.service.EmployeeService;
import com.example.rqchallenge.employees.test.ControllerIntegrationTest;
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

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest extends ControllerIntegrationTest {

    @Mock
    EmployeeMapper employeeMapper;

    @Mock
    EmployeeService employeeService;

    @InjectMocks
    EmployeeController employeeController;

    @Test
    void itFetchesAllEmployees() {
        val employeeList = Mono.just(List.of(new Employee(1L, "bob", 3, 3, ""),
                new Employee(2L, "alice", 3, 4, "")));

        val expected = Mono.just(List.of(new EmployeeResponse(1L, "bob", 3, 3, ""),
                new EmployeeResponse(2L, "alice", 3, 4, "")));

        Mockito.when(employeeService.getAllEmployees()).thenReturn(employeeList);
        Mockito.when(employeeMapper.getEmployeesResponseFrom(employeeList)).thenReturn(expected);

        val result = employeeController.getAllEmployees();

        Assertions.assertEquals(result.getBody().block(), expected.block());
    }
}
