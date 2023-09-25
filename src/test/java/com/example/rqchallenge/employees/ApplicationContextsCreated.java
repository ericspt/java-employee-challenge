package com.example.rqchallenge.employees;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Configuration
public class ApplicationContextsCreated {
    private static final Set<ApplicationContext> contexts = new HashSet<>();
    private static int created = 0;

    @EventListener(ApplicationStartedEvent.class)
    public void started(ApplicationStartedEvent event) {
        synchronized (ApplicationContextsCreated.class) {
            created++;
            contexts.add(event.getApplicationContext());
        }
    }

    @EventListener(ContextClosedEvent.class)
    public void stopped(ContextClosedEvent event) {
        synchronized (ApplicationContextsCreated.class) {
            contexts.remove(event.getApplicationContext());
            if (contexts.isEmpty()) {
                log.info("\nNumber of ApplicationContext instances created for testing - {}\n", created);
            }
        }
    }
}
