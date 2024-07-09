package com.pablo.model;

import jakarta.persistence.*;

@Table(name = "request_counter", uniqueConstraints = @UniqueConstraint(columnNames = "login"))
@Entity
public class RequestCounter {

    public RequestCounter() {
    }

    public RequestCounter(String login, Long requestCount) {
        this.login = login;
        this.requestCount = requestCount;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "request_count", nullable = false)
    private Long requestCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String requestedLogin) {
        this.login = requestedLogin;
    }

    public Long getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(Long requestCount) {
        this.requestCount = requestCount;
    }
}
