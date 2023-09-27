package com.example.rqchallenge.employees.rest.mapper;

import com.example.rqchallenge.employees.domain.Employee;
import com.example.rqchallenge.employees.rest.response.EmployeeResponse;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class EmployeeMapperTest {

    @InjectMocks
    EmployeeMapper employeeMapper;

    @Test
    void itMapsEmployeeListToResponse() {
        val employeeList = Mono.just(List.of(new Employee(1L, "bob", 3, 3, ""),
                new Employee(2L, "alice", 3, 4, "")));

        val expected = Mono.just(List.of(new EmployeeResponse(1L, "bob", 3, 3, ""),
                new EmployeeResponse(2L, "alice", 3, 4, "")));

        val result = employeeMapper.getEmployeesResponseFrom(employeeList);

        Assertions.assertEquals(result.block(), expected.block());
    }

    @Test
    void itMapsEmployeeToResponse() {
        val employee = Mono.just(new Employee(1L, "bob", 3, 3, ""));

        val expected = Mono.just(new EmployeeResponse(1L, "bob", 3, 3, ""));

        val result = employeeMapper.getEmployeeResponseFrom(employee);

        Assertions.assertEquals(result.block(), expected.block());
    }
}
