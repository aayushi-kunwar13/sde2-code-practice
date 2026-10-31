package com.practice.phonepe.questions.bookmyshow.repository;

import com.practice.phonepe.questions.bookmyshow.entities.Theatre;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryTheatreRepository implements TheatreRepository {

    private final Map<String, Theatre> theatresById = new LinkedHashMap<>();

    @Override
    public void save(Theatre theatre) {
        theatresById.put(theatre.getId(), theatre);
    }

    @Override
    public Optional<Theatre> findById(String theatreId) {
        return Optional.ofNullable(theatresById.get(theatreId));
    }

    @Override
    public List<Theatre> findByCity(String city) {
        return theatresById.values().stream()
                .filter(theatre -> theatre.getCity().equalsIgnoreCase(city))
                .collect(Collectors.toList());
    }

    @Override
    public List<Theatre> findAll() {
        return new ArrayList<>(theatresById.values());
    }
}
