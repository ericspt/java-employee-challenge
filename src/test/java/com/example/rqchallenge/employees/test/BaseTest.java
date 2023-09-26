package com.example.rqchallenge.employees.test;

import com.example.rqchallenge.employees.employees.RqChallengeApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@SpringBootTest(classes = {RqChallengeApplication.class})
public @interface BaseTest {

}
