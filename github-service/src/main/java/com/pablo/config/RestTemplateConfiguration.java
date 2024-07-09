package com.pablo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguration {
    private final String gitHubBaseUrl;

    public RestTemplateConfiguration(@Value("${gitHubBaseUrl}")String gitHubBaseUrl) {
        this.gitHubBaseUrl = gitHubBaseUrl;
    }
    @Bean
    public RestTemplate restTemplate(){
        RestTemplateBuilder builder = new RestTemplateBuilder()
                .rootUri(gitHubBaseUrl)
                .defaultHeader("Accept", "application/json")
                .defaultHeader("Content-Type", "application/json");
        return builder.build();
    }
}
