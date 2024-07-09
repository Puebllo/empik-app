package com.pablo.records;

import java.time.LocalDateTime;

public record GitHubUserInfo(Long id, String login, String name, String type, String avatarUrl, LocalDateTime createdAt, Double calculations) {



}
