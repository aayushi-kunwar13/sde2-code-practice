package com.practice.phonepe.interview.repository;

import com.practice.phonepe.interview.entities.Contestant;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryContestantRepository implements ContestantRepository {

    private final Map<String, Contestant> contestantsById = new LinkedHashMap<>();

    @Override
    public void save(Contestant contestant) {
        contestantsById.put(contestant.getId(), contestant);
    }

    @Override
    public Optional<Contestant> findById(String contestantId) {
        return Optional.ofNullable(contestantsById.get(contestantId));
    }

    @Override
    public List<Contestant> findAll() {
        return new ArrayList<>(contestantsById.values());
    }
}
