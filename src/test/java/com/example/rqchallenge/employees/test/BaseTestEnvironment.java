package com.example.rqchallenge.employees.test;

import com.example.rqchallenge.employees.employees.RqChallengeApplication;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@SpringBootTest(classes = {RqChallengeApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public @interface BaseTestEnvironment {

}
