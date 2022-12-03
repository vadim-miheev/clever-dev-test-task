package com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@AllArgsConstructor
public class AppConfig {

    private Environment env;

    @Bean
    public WebClient webClientSetup() {
        return WebClient.create(env.getProperty("app.old-system-url", "http://localhost:8080"));
    }
}
