package com.irctc.app_irctc.config;

import org.neo4j.driver.Driver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.neo4j.config.EnableNeo4jAuditing;
import org.springframework.data.neo4j.core.transaction.ReactiveNeo4jTransactionManager;
import org.springframework.transaction.ReactiveTransactionManager;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Configuration
@EnableNeo4jAuditing
public class Neo4jConfig {

    @Bean
    public ReactiveTransactionManager reactiveTransactionManager(Driver driver) {
        return new ReactiveNeo4jTransactionManager(driver);
    }

    // Auditor for auditing fields like createdAt / updatedAt
    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.of("system"); // ✅ returns Optional<String>
    }
}
