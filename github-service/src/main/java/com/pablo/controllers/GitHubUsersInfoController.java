package com.pablo.controllers;


import com.pablo.service.GitHubUserInfoService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GitHubUsersInfoController {

    private final GitHubUserInfoService gitHubUserInfoService;

    public GitHubUsersInfoController(GitHubUserInfoService gitHubUserInfoService) {
        this.gitHubUserInfoService = gitHubUserInfoService;
    }

    @GetMapping(value = "/users/{login}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getGithubUsersInfo(@PathVariable String login){
        return gitHubUserInfoService.getGitHubUserInfoByLogin(login);
    }
}
