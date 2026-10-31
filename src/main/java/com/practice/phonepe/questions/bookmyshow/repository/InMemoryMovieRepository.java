package com.practice.phonepe.questions.bookmyshow.repository;

import com.practice.phonepe.questions.bookmyshow.entities.Movie;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryMovieRepository implements MovieRepository {

    private final Map<String, Movie> moviesById = new LinkedHashMap<>();

    @Override
    public void save(Movie movie) {
        moviesById.put(movie.getId(), movie);
    }

    @Override
    public Optional<Movie> findById(String movieId) {
        return Optional.ofNullable(moviesById.get(movieId));
    }

    @Override
    public List<Movie> findAll() {
        return new ArrayList<>(moviesById.values());
    }

    @Override
    public void deleteById(String movieId) {
        moviesById.remove(movieId);
    }
}
