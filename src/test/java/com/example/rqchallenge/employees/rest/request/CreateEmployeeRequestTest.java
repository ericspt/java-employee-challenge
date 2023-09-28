package com.example.rqchallenge.employees.rest.request;

import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class CreateEmployeeRequestTest {
    @Test
    void itShouldAssessThatRequestIsValid() {
        val request = new CreateEmployeeRequest(
                Map.of("name", "bob", "salary", "123", "age", "11")
        );

        Assertions.assertTrue(request.isValid());
    }

    @Test
    void itShouldAssessThatRequestIsInvalidSalaryString() {
        val request = new CreateEmployeeRequest(
                Map.of("name", "bob", "salary", "12a3", "age", "11")
        );

        Assertions.assertFalse(request.isValid());
    }

    @Test
    void itShouldAssessThatRequestIsInvalidEmptyProp() {
        val request = new CreateEmployeeRequest(
                Map.of("name", "bob", "age", "11")
        );

        Assertions.assertFalse(request.isValid());
    }

    @Test
    void itShouldAssessThatRequestIsInvalidSalaryLong() {
        val request = new CreateEmployeeRequest(
                Map.of("name", "bob", "salary", 123, "age", "11")
        );

        Assertions.assertFalse(request.isValid());
    }
}
