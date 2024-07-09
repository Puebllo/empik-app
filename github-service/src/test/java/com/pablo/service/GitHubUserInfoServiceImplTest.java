package com.pablo.service;

import com.pablo.model.RequestCounter;
import com.pablo.records.GitHubUserInfoAPIResponse;
import com.pablo.repositories.RequestCounterRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class GitHubUserInfoServiceImplTest {

    @Mock
    RequestCounterRepository requestCounterRepository;

    @InjectMocks
    GitHubUserInfoServiceImpl gitHubUserInfoService;

    @BeforeAll
    public static void init() {
        MockitoAnnotations.openMocks(GitHubUserInfoServiceImplTest.class);
    }

    @Test
    public void shouldInsertNewRecordRequestCountForLogin(){
        RequestCounter savedRequestCounter = new RequestCounter("pablo", 1L);
        savedRequestCounter.setId(1L);
        Mockito.when(requestCounterRepository.findByLogin("pablo")).thenReturn(Optional.empty());
        Mockito.when(requestCounterRepository.save(Mockito.any(RequestCounter.class))).thenReturn(savedRequestCounter);

        RequestCounter result = gitHubUserInfoService.updateRequestCount("pablo");

        Assertions.assertThat(result.getId()).isNotNull();
        Assertions.assertThat(result.getRequestCount()).isEqualTo(1);
    }

    @Test
    public void shouldUpdateRequestCountForLogin(){
        RequestCounter previouslySavedCounter = new RequestCounter("pablo", 1L);
        previouslySavedCounter.setId(1L);
        Mockito.when(requestCounterRepository.findByLogin("pablo")).thenReturn(Optional.of(previouslySavedCounter));
        Mockito.when(requestCounterRepository.save(Mockito.any(RequestCounter.class))).thenReturn(previouslySavedCounter);

        RequestCounter result = gitHubUserInfoService.updateRequestCount("pablo");

        Assertions.assertThat(result.getId()).isNotNull();
        Assertions.assertThat(result.getRequestCount()).isEqualTo(2);

    }

    @Test
    public void shouldReturnBadRequestOnNullLogin(){
        ResponseEntity<?> response = ResponseEntity.badRequest().body("Request is missing login parameter !");

        ResponseEntity<?> responseEntity = gitHubUserInfoService.getGitHubUserInfoByLogin(null);

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(response.getStatusCode());
        Assertions.assertThat(responseEntity.getBody()).isEqualTo(response.getBody());

    }

    @DisplayName("Magic calculation test cases")
    @ParameterizedTest(name = "{0} followers and {1} public repos should give {2}")
    @CsvSource(value = {"24,200,50.5", "null,null,-1", "null,200,-1" , "24,null,-1", "-24,-200,-1", "-24,200,-1", "24,-200,-1"}, nullValues={"null"})
    public void calculateTestCases(Long follower, Long publicRepos, Double result){
        GitHubUserInfoAPIResponse apiResponse = new GitHubUserInfoAPIResponse(10L,"pablo","Pablo","User",follower,"avatar_url", LocalDateTime.now(), publicRepos);

        Double calculation = gitHubUserInfoService.magicCalculation(apiResponse);

        Assertions.assertThat(calculation).isNotNaN();
        Assertions.assertThat(calculation).isFinite();
        Assertions.assertThat(calculation).isEqualTo(result);
    }


}