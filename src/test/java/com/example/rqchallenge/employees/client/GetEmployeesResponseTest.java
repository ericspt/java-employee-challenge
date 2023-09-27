package com.example.rqchallenge.employees.client;

import com.example.rqchallenge.employees.domain.Employee;
import com.example.rqchallenge.employees.exception.NoEmployeesFoundException;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class GetEmployeesResponseTest {
    @Test
    void itConvertsObjectToDomain() {
        val response = new GetEmployeesResponse("success",
                List.of(new GetEmployeeResponse(1L, "bob", 3, 3, "")),
                "");
        val expected = List.of(new Employee(1L, "bob", 3, 3, ""));

        val result = response.toDomain();

        assert result.equals(expected);
    }

    @Test
    void itConvertsNullEmployeeToDomain() {
        val response = new GetEmployeesResponse("failed", List.of(), "");

        Assertions.assertThrows(NoEmployeesFoundException.class, response::toDomain);
    }
}
