package com.naverrain.grocery.persistence.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.naverrain.grocery.persistence.repository")
@EntityScan(basePackages = "com.naverrain.grocery.persistence.entity")
@EnableTransactionManagement
public class PersistenceConfig {
}
