package com.example.rqchallenge.employees.client;

import com.example.rqchallenge.employees.domain.Employee;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetEmployeeResponseTest {
    @Test
    void itConvertsObjectToDomain() {
        val response = new GetEmployeeResponse(1L, "bob", 3, 3, "");
        val expected = new Employee(1L, "bob", 3, 3, "");

        val result = response.toDomain();

        assert result.equals(expected);
    }
}
