package com.pablo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class RestClientConfiguration {


    private final String gitHubBaseUrl;

    public RestClientConfiguration(@Value("${gitHubBaseUrl}") String gitHubBaseUrl) {
        this.gitHubBaseUrl = gitHubBaseUrl;
    }

    @Bean
    public RestClient gitHubRestClient(){
        return RestClient.builder()
                .requestFactory(new HttpComponentsClientHttpRequestFactory())
                .baseUrl(gitHubBaseUrl)
                .defaultHeader("Accept", "application/json")
                .defaultHeader("Content-Type", "application/json")
                .build();
    }


}