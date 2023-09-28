# Employee Application - Coding Challenge

## Context
This is a Spring Boot Web reactive application. It calls a [dummy API](https://dummy.restapiexample.com/) to fetch,
add and delete employee data.

It exposes a number of endpoints that proxy over to the external dummy API.

## Layers
This reactive application obeys basic DDD principles. The application has:
- a presentational layer, represented by the
[IEmployeeController.java](src/main/java/com/example/rqchallenge/employees/rest/IEmployeeController.java) file, where
available endpoints are also shown and a 
[mapper](src/main/java/com/example/rqchallenge/employees/rest/mapper/EmployeeMapper.java)
- a domain layer, represented by the [Employee](src/main/java/com/example/rqchallenge/employees/domain/Employee.java)
record, as well as the service hosting business logic i.e. 
[EmployeeService](src/main/java/com/example/rqchallenge/employees/service/EmployeeService.java)
- a network layer, connecting the dummy API to our application, represented by the
[EmployeeClient](src/main/java/com/example/rqchallenge/employees/client/EmployeeClient.java)

## Testing
For testing:
- `WebTestClient` is used for controller integration tests, each time with a different port
- `Mockito` is used to Mock different classes, as well as to inject those into the classes undergoing testing

## Logging
In this project, the `@Slf4j` API is being used, aiming for readability and simplicity. Logging may be improved by
creating a custom logger that acts as a wrapper around this API and introduces smarter, more hidden logging.

## Exception Handling
Domain layer exceptions are defined in [here](src/main/java/com/example/rqchallenge/employees/exception). These are
thrown, logged and passed further to the controller that picks them up and passes the status further.
