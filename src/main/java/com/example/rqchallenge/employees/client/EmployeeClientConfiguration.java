package com.example.rqchallenge.employees.client;

import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;

@Configuration
@Slf4j
public class EmployeeClientConfiguration {

    private static final String EMPLOYEE_API_URL = "https://dummy.restapiexample.com/api/v1";

    @Bean
    @Qualifier("employeeWebClient")
    public WebClient employeeWebClient() throws SSLException {
        var s = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        var httpClient = HttpClient.create().secure(t -> t.sslContext(s));
        return WebClient.builder()
                .baseUrl(EMPLOYEE_API_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}
