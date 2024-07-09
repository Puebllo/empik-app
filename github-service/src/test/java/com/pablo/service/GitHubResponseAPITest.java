package com.pablo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pablo.GithubServiceApplication;
import com.pablo.records.GitHubUserInfo;
import com.pablo.records.GitHubUserInfoAPIResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.LocalDateTime;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = GithubServiceApplication.class)
public class GitHubResponseAPITest {

    @Autowired
    private GitHubUserInfoServiceImpl service;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void init() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }
    @SuppressWarnings("unchecked")
    @Test
    public void shouldSuccessfullyReturnResponseFromAPI() throws Exception{
        GitHubUserInfoAPIResponse apiResponse = new GitHubUserInfoAPIResponse(10L,"puebllo","Puebllo","User",24L,"avatar_url", LocalDateTime.now(), 200L);

        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("https://api.github.com/users/puebllo")))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(apiResponse))
                );

        ResponseEntity<GitHubUserInfo> responseEntity = (ResponseEntity<GitHubUserInfo>) service.getGitHubUserInfoByLogin("puebllo");

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity.getBody().login()).isEqualTo("puebllo");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldFailReturnResponseFromAPI() throws Exception{

        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("https://api.github.com/users/puebllo")))
                .andRespond(withServerError().body("Error getting response from GitHub API"));

        ResponseEntity<String> responseEntity = (ResponseEntity<String>) service.getGitHubUserInfoByLogin("puebllo");

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        Assertions.assertThat(responseEntity.getBody().startsWith("Error getting response from GitHub API")).isTrue();

    }

}
