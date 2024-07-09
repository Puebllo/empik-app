package com.pablo.service;


import com.pablo.model.RequestCounter;
import com.pablo.records.GitHubUserInfo;
import com.pablo.records.GitHubUserInfoAPIResponse;
import com.pablo.repositories.RequestCounterRepository;
import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;


@Service
public class GitHubUserInfoServiceImpl implements GitHubUserInfoService {

    private static final Logger log = LoggerFactory.getLogger(GitHubUserInfoServiceImpl.class);
    private final RequestCounterRepository requestCounterRepository;

    private final RestTemplate restTemplate;

    @Autowired
    public GitHubUserInfoServiceImpl(RequestCounterRepository requestCounterRepository, RestTemplate restTemplate) {
        this.requestCounterRepository = requestCounterRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseEntity<?> getGitHubUserInfoByLogin(@NonNull String login) {

        if (StringUtils.isEmpty(login)){
            return ResponseEntity.badRequest().body("Request is missing login parameter !");
        }

        ResponseEntity<?> response = getResponseFromGitHubAPI(login);

        updateRequestCount(login);

        if (!response.getStatusCode().is2xxSuccessful()) {
            log.error(response.getBody().toString());
            return response;
        }

        GitHubUserInfoAPIResponse apiResponse = (GitHubUserInfoAPIResponse) response.getBody();

        Double calculations = magicCalculation(apiResponse);

        return ResponseEntity.ok(new GitHubUserInfo(apiResponse.id(), apiResponse.login(), apiResponse.name(), apiResponse.type(), apiResponse.avatarUrl(), apiResponse.createdAt(), calculations));
    }

    public ResponseEntity<?> getResponseFromGitHubAPI(@NonNull String login) {
        ResponseEntity<?> toReturn;
        try {
            String resourceUrl = "/users/"+login;
            toReturn = restTemplate.getForEntity(resourceUrl , GitHubUserInfoAPIResponse.class);
        }catch (RestClientException rce){
            toReturn = ResponseEntity.internalServerError().body("Error getting response from GitHub API - " + rce.getClass() + ": " + rce.getMessage());
        }
        return toReturn;
    }


    public Double magicCalculation(GitHubUserInfoAPIResponse apiResponse) {
        if (apiResponse.followers()!=null && apiResponse.followers() >0 && apiResponse.publicRepos()!=null && apiResponse.publicRepos()>0){
            return Double.valueOf( ((double) 6 / apiResponse.followers()) * (2 + apiResponse.publicRepos()) );
        }
        return -1D;
    }

    public RequestCounter updateRequestCount(@NonNull String login){
        RequestCounter requestCounter;
        Optional<RequestCounter> requestCounterOptional = requestCounterRepository.findByLogin(login);

        if (requestCounterOptional.isEmpty()){
            requestCounter = new RequestCounter(login, 1L);
        }else{
            requestCounter = requestCounterOptional.get();
            requestCounter.setRequestCount(requestCounter.getRequestCount()+1);
        }

        return requestCounterRepository.save(requestCounter);
    }

}
