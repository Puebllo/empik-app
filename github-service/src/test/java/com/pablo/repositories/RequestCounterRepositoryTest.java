package com.pablo.repositories;

import com.pablo.model.RequestCounter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;


@DataJpaTest
class RequestCounterRepositoryTest {

    @Autowired
    RequestCounterRepository repository;

    static RequestCounter requestCounter;

    @BeforeAll
    public static void beforeAll(){
        requestCounter = new RequestCounter();
        requestCounter.setLogin("pablo");
        requestCounter.setRequestCount(20L);
    }

    @Test
    public void saveRequestCounter(){
        RequestCounter persistedRequestCounter = repository.save(requestCounter);
        Assertions.assertThat(persistedRequestCounter).isNotNull();
        Assertions.assertThat(persistedRequestCounter.getId()).isNotNull();

        Optional<RequestCounter> foundRequestCounter = repository.findById(persistedRequestCounter.getId());
        Assertions.assertThat(foundRequestCounter.isPresent()).isTrue();
        Assertions.assertThat(foundRequestCounter.get().getLogin()).isEqualTo(persistedRequestCounter.getLogin());

    }

    @Test
    public void findRequestCounterByLoginValidUsername(){
        RequestCounter persistedRQ = repository.save(requestCounter);

        Optional<RequestCounter> foundRequestCounter = repository.findByLogin("pablo");
        Assertions.assertThat(foundRequestCounter.isPresent()).isTrue();
        Assertions.assertThat(foundRequestCounter.get().getLogin()).isEqualTo(persistedRQ.getLogin());
    }


}