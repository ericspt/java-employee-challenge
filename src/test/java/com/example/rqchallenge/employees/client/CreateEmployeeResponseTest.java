package com.example.rqchallenge.employees.client;

import com.example.rqchallenge.employees.domain.Employee;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CreateEmployeeResponseTest {
    @Test
    void itShouldConvertToDomain() {
        val createResponse = new CreateEmployeeResponse(
                "success",
                new CreateEmployeeDataResponse("bob", "12", "11", 3L)
        );

        val expected = new Employee(3L, "bob", 12, 11, "");

        val result = createResponse.toDomain();

        Assertions.assertEquals(result, expected);
    }
}
