package com.pablo.controllers;

import com.pablo.records.GitHubUserInfo;
import com.pablo.service.GitHubUserInfoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

@WebMvcTest(GitHubUsersInfoController.class)
@AutoConfigureMockMvc(addFilters = false)
class GitHubUsersInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GitHubUserInfoService gitHubUserInfoService;

    @Test
    public void shouldReturnSuccessfully() throws Exception {
        GitHubUserInfo ghui = new GitHubUserInfo(10L,"puebllo","Pablo", "UserType", "avatar_url", LocalDateTime.now(), 50.5D);
        ResponseEntity response = ResponseEntity.ok(ghui);

        Mockito.when(gitHubUserInfoService.getGitHubUserInfoByLogin(Mockito.any(String.class))).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/puebllo")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.login").value("puebllo"));

    }

}