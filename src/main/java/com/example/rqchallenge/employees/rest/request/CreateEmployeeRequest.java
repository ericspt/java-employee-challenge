package com.example.rqchallenge.employees.rest.request;

import java.util.Map;

public record CreateEmployeeRequest(Map<String, Object> employeeInput) {
    public boolean isValid() {
        var name = employeeInput.get("name");
        var salary = employeeInput.get("salary");
        var age = employeeInput.get("age");

        if (name == null || salary == null || age == null) {
            return false;
        }

        if (!(name instanceof String) || !(salary instanceof String) || !(age instanceof String)) {
            return false;
        }

        try {
            Long.parseLong((String) salary);
            Long.parseLong((String) age);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }
}
