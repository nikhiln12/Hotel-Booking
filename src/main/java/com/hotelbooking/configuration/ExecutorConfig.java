package com.hotelbooking.configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExecutorConfig {

    @Bean
    public ExecutorService executorService() {
        // You can adjust the thread pool size according to your application's needs.
        return Executors.newFixedThreadPool(10);
    }
}