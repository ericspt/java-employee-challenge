package com.example.rqchallenge.employees.client;

import com.example.rqchallenge.employees.domain.Employee;
import com.example.rqchallenge.employees.exception.EmployeeNotFoundException;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetEmployeeByIdResponseTest {
    @Test
    void itConvertsObjectToDomain() {
        val response = new GetEmployeeByIdResponse("success",
                new GetEmployeeResponse(1L, "bob", 3, 3, ""),
                "");
        val expected = new Employee(1L, "bob", 3, 3, "");

        val result = response.toDomain();

        assert result.equals(expected);
    }

    @Test
    void itConvertsNullEmployeeToDomain() {
        val response = new GetEmployeeByIdResponse("failed", null, "");

        Assertions.assertThrows(EmployeeNotFoundException.class, response::toDomain);
    }
}
