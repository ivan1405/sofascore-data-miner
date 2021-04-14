package com.sports.data.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.net.http.HttpClient;
import java.time.Duration;

@Configuration
@EnableJpaRepositories(basePackages = "com.sports.data.crud.repository")
@EntityScan(basePackages = "com.sports.data.crud.entity")
@ComponentScan(basePackages = "com.sports.data")
public class DataMinerConfig {

    @Bean
    public HttpClient httpClient() {
        return HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(20))
                .build();
    }

}
