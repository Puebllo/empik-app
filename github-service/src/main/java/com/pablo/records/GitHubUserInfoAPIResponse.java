package com.pablo.records;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record GitHubUserInfoAPIResponse(Long id, String login, String name, String type, Long followers,
                                        @JsonProperty("avatar_url") String avatarUrl,
                                        @JsonProperty("created_at")LocalDateTime createdAt,
                                        @JsonProperty("public_repos")Long publicRepos) {



}
