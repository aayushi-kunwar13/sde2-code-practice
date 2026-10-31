package com.practice.phonepe.interview.repository;

import com.practice.phonepe.interview.entities.Contestant;

import java.util.List;
import java.util.Optional;

public interface ContestantRepository {

    void save(Contestant contestant);

    Optional<Contestant> findById(String contestantId);

    List<Contestant> findAll();
}
