package com.pablo.repositories;

import com.pablo.model.RequestCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RequestCounterRepository extends JpaRepository<RequestCounter, Long> {

    Optional<RequestCounter> findByLogin(String login);

}
