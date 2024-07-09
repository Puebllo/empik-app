package com.pablo.service;

import org.springframework.http.ResponseEntity;

public interface GitHubUserInfoService {

    ResponseEntity<?> getGitHubUserInfoByLogin(String login);
}
